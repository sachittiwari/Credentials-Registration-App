import React, { useEffect, useState } from "react";
import axios from "axios";

// Interface for Credential Response
interface Credential {
  id: number;
  clientId: string;
  clientSecret: string;
  creationDate: string;
  expiryDate: string;
  userId: number;
  orgId: number;
}

// Interface for Credential Request
interface CredentialRequest {
  userId: number;
  organizationId: number;
}

const CredentialComponent: React.FC = () => {
  const [credentials, setCredentials] = useState<Credential[]>([]);
  const [loading, setLoading] = useState(true);
  const [alertMessage, setAlertMessage] = useState<string | null>(null); // For client secret alert

  // Org Id Fetch
  const orgId = sessionStorage.getItem("orgId");
  const orgFromSession = orgId ? parseInt(orgId, 10) : null;
  const organizationId = orgFromSession ?? 0;

  // User Id Fetch
  const userId = sessionStorage.getItem("userId");
  const userFromSession = userId ? parseInt(userId, 10) : null;
  const user = userFromSession ?? 0;

  const token = sessionStorage.getItem("token");

  // Fetch credentials on component load
  useEffect(() => {
    const fetchCredentials = async () => {
      try {
        const response = await axios.get(
          `${import.meta.env.VITE_BACKEND_API_BASE_URL}/credentials/?`,
          {
            headers: {
              Authorization: `Bearer ${token}`, // Add the Bearer token here
              "Content-Type": "application/json",
            },
            params: { organizationId },
          }
        );
        setCredentials(response.data);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching credentials:", error);
        setLoading(false);
      }
    };

    fetchCredentials();
  }, [organizationId, token]);

  // Handle Create New Credential
  const handleCreateCredential = async () => {
    const newCred: CredentialRequest = {
      userId: user,
      organizationId: organizationId,
    };
    try {
      const response = await axios.post(
        `${import.meta.env.VITE_BACKEND_API_BASE_URL}/credentials/`,
        newCred,
        {
          headers: {
            Authorization: `Bearer ${token}`, // Add the Bearer token here
            "Content-Type": "application/json",
          },
        }
      );
      setCredentials((prev) => [...prev, response.data]); // Add new credential to list
      setAlertMessage(`Client Secret: ${response.data.clientSecret}`); // Display client secret
    } catch (error) {
      console.error("Error creating credential:", error);
    }
  };

  // Handle Delete Credential
  const handleDeleteCredential = async (id: number) => {
    try {
      await axios.delete(
        `${import.meta.env.VITE_BACKEND_API_BASE_URL}/credentials/${id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setCredentials((prev) => prev.filter((cred) => cred.id !== id)); // Remove deleted credential
    } catch (error) {
      console.error("Error deleting credential:", error);
    }
  };

  // Handle Update Credential
  const handleUpdateCredential = async (id: number) => {
    try {
      const response = await axios.patch(
        `${import.meta.env.VITE_BACKEND_API_BASE_URL}/credentials/${id}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );
      setCredentials((prev) =>
        prev.map((cred) =>
          cred.id === id ? { ...cred, ...response.data } : cred
        )
      ); // Update credential in the list
      setAlertMessage(`Updated Client Secret: ${response.data.clientSecret}`); // Display client secret
    } catch (error) {
      console.error("Error updating credential:", error);
    }
  };

  if (loading) {
    return <p>Loading credentials...</p>;
  }

  return (
    <div>
      <h3>Credentials Management</h3>

      {/* Display Alert Message */}
      {alertMessage && (
        <div
          className="alert alert-info alert-dismissible fade show"
          role="alert"
        >
          {alertMessage}
          <button
            type="button"
            className="btn-close"
            aria-label="Close"
            onClick={() => setAlertMessage(null)}
          ></button>
        </div>
      )}

      <button className="btn btn-success" onClick={handleCreateCredential}>
        Create New Credential
      </button>
      <table className="table table-bordered mt-3">
        <thead>
          <tr>
            <th>Client ID</th>
            <th>Creation Date</th>
            <th>Expiry Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {credentials.map((cred) => (
            <tr key={cred.id}>
              <td>{cred.clientId}</td>
              <td>{cred.creationDate}</td>
              <td>{cred.expiryDate}</td>
              <td>
                <button
                  className="btn btn-danger btn-sm"
                  onClick={() => handleDeleteCredential(cred.id)}
                >
                  Delete
                </button>
                <button
                  className="btn btn-primary btn-sm ml-2"
                  onClick={() => handleUpdateCredential(cred.id)}
                >
                  Update
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CredentialComponent;
