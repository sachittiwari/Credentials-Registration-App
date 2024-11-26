import React, { useEffect, useState } from "react";
import axios from "axios";
import { UserResponseInfo } from "../types/User";
import CredentialComponent from "./Credentials";

interface OrganizationProps {
  user: UserResponseInfo;
}

interface UserOrgMapping {
  userId: number;
  organizationIds: Number[];
}

export interface OrganizationResponse {
  id: number;
  name: string;
  vatNumber: string;
  sapId: string;
}

const OrganizationComponent: React.FC<OrganizationProps> = ({ user }) => {
  const [organizations, setOrganizations] = useState<OrganizationResponse[]>(
    []
  );
  const [notCreated, setNotCreated] = useState(true);
  const [newUser, setNewUser] = useState(true);
  const [showCredsGrid, setShowCredsGrid] = useState(false);
  const [selectedOrganizations, setSelectedOrganizations] = useState<number[]>(
    []
  );
  const [selectedSessionOrganization, setSelectedSessionOrganization] =
    useState<number>();
  const [userPartOfSingleOrg, setUserPartOfSingleOrg] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [errorMessage, setErrorMessage] = useState<string>("");

  const token = sessionStorage.getItem("token");
  const userId = sessionStorage.getItem("userId");

  const handleCheckboxChange = (id: number) => {
    setSelectedOrganizations(
      (prevSelected) =>
        prevSelected.includes(id)
          ? prevSelected.filter((orgId) => orgId !== id) // Unselect
          : [...prevSelected, id] // Select
    );
  };

  const handleSessionOrgSubmit = () => {
    if (selectedSessionOrganization === 0) {
      setErrorMessage("Please select one organization.");
      return;
    }
    console.log(
      "Selected Organization for session is: ",
      selectedSessionOrganization
    );
    const orgId = selectedSessionOrganization ?? 0;
    sessionStorage.setItem("orgId", orgId.toString());
    setShowCredsGrid(true);
  };

  const handleSubmit = async () => {
    if (selectedOrganizations.length === 0) {
      setErrorMessage("Please select at least one organization.");
      return;
    }

    // If validation passes, clear error message and proceed with submission
    setErrorMessage("");
    console.log("Selected organizations:", selectedOrganizations);

    setLoading(true); // Start loading when API call is being made

    try {
      const userFromSession = sessionStorage.getItem("userId");
      console.log(userFromSession);
      const userId = userFromSession ? parseInt(userFromSession, 10) : null;
      const mapping: UserOrgMapping = {
        userId: userId ?? 0,
        organizationIds: selectedOrganizations,
      };
      // Make POST API call to your backend with user data
      const url =
        import.meta.env.VITE_BACKEND_API_BASE_URL + "/user-org-mapping/";
      console.log("URL is" + url);
      const response = await axios.post(url, mapping, {
        headers: {
          Authorization: `Bearer ${token}`, // Add the Bearer token here
          "Content-Type": "application/json",
        },
      });

      // Check the response status or message
      if (response.status === 200) {
        console.log(
          "User has been linked to Organization successfully",
          response.data
        );
        setNotCreated(false);
      } else {
        console.error("Failed to create mapping", response.data);
        setError("Failed to create user");
      }
    } catch (error) {
      console.error("Error during API call", error);
      setError("Error while communicating with the backend");
    } finally {
      // Stop loading when done
      setLoading(false);
    }
  };

  useEffect(() => {
    // Fetch organizations
    const fetchOrganizations = async () => {
      setLoading(true);
      try {
        if (user.organizations.length === 1) {
          const orgId = user.organizations[0].id;
          console.log("Inside org equal to 1: ", orgId);
          sessionStorage.setItem("orgId", orgId.toString());
          setUserPartOfSingleOrg(true);
          setNewUser(false);
          setNotCreated(false);
          setLoading(false);
          setShowCredsGrid(true);
        } else if (user.organizations.length > 1) {
          console.log("Inside org greater than 1");
          const userFromSession = userId ? parseInt(userId, 10) : null;
          const response = await axios.get(
            `${
              import.meta.env.VITE_BACKEND_API_BASE_URL
            }/user-org-mapping/?userId=${userFromSession}`,
            {
              headers: {
                Authorization: `Bearer ${token}`, // Add the Bearer token here
                "Content-Type": "application/json",
              },
            }
          );
          setOrganizations(response.data);
          setNewUser(false);
          setNotCreated(false);
          setLoading(false);
        } else {
          const response = await axios.get(
            `${import.meta.env.VITE_BACKEND_API_BASE_URL}/organization/`,
            {
              headers: {
                Authorization: `Bearer ${token}`, // Add the Bearer token here
                "Content-Type": "application/json",
              },
            }
          );
          setOrganizations(response.data);
          setLoading(false);
        }
      } catch (error) {
        console.error("Error fetching organizations:", error);
        setLoading(false);
      }
    };

    fetchOrganizations();
  }, [user]);

  if (loading) {
    return <p>Loading organizations...</p>;
  }

  if (!notCreated && newUser) {
    return (
      <p>Mapping is created successfully. Please logout and login again!</p>
    );
  }

  return (
    <div>
      {notCreated && !showCredsGrid && (
        <>
          <h3>Please select one or more organizations from the list below.</h3>
          {error && <p className="error">{error}</p>}
          {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
          <form>
            {organizations.map((org) => (
              <div key={org.id} className="form-check">
                <input
                  className="form-check-input"
                  type="checkbox"
                  id={org.id.toString()}
                  value={org.name}
                  checked={selectedOrganizations.includes(org.id)}
                  onChange={() => handleCheckboxChange(org.id)}
                />
                <label className="form-check-label" htmlFor={org.id.toString()}>
                  {org.name}
                </label>
              </div>
            ))}
            <button
              type="button"
              className="btn btn-primary mt-3"
              onClick={handleSubmit}
            >
              Submit
            </button>
          </form>
        </>
      )}
      {!newUser && !userPartOfSingleOrg && !showCredsGrid && (
        <>
          <h3>
            You are part of multiple organizations. Please select desired
            organization for the current session from below list.
          </h3>
          {error && <p className="error">{error}</p>}
          {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
          <form>
            {organizations.map((org) => (
              <div key={org.id} className="form-check">
                <input
                  className="form-check-input"
                  type="radio"
                  id={org.id.toString()}
                  name="organization"
                  value={org.name}
                  checked={selectedSessionOrganization === org.id}
                  onChange={() => setSelectedSessionOrganization(org.id)}
                />
                <label className="form-check-label" htmlFor={org.id.toString()}>
                  {org.name}
                </label>
              </div>
            ))}
            <button
              type="button"
              className="btn btn-primary mt-3"
              onClick={handleSessionOrgSubmit}
            >
              Submit
            </button>
          </form>
        </>
      )}
      {showCredsGrid && <CredentialComponent />}
    </div>
  );
};

export default OrganizationComponent;
