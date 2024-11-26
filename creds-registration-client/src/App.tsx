import React, { useState } from "react";
import "./App.css";
import {
  GoogleOAuthProvider,
  GoogleLogin,
  googleLogout,
  CredentialResponse,
} from "@react-oauth/google";
import { jwtDecode } from "jwt-decode";
import axios from "axios";
import { UserInfo, UserResponseInfo, DecodedToken } from "./types/User";
import OrganizationComponent from "./components/Organization";

const App: React.FC = () => {
  const clientId = import.meta.env.VITE_GOOGLE_CLIENT_ID;
  const [userResponse, setUserResponse] = useState<UserResponseInfo | null>(
    null
  );
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleLoginSuccess = (credentialResponse: CredentialResponse) => {
    if (credentialResponse.credential) {
      console.log(credentialResponse.credential);
      sessionStorage.setItem("token", credentialResponse.credential);
      const decoded: DecodedToken = jwtDecode(credentialResponse.credential);
      // Make the backend API POST call to create the user
      createUserInBackend(decoded);
    } else {
      console.error("Credential is undefined");
    }
  };

  const handleLogout = () => {
    googleLogout();
    setUserResponse(null);
    sessionStorage.clear();
  };

  // Function to make the POST request to the backend API
  const createUserInBackend = async (userData: DecodedToken) => {
    setLoading(true); // Start loading when API call is being made

    try {
      const user: UserInfo = {
        subjectId: userData.email,
        name: userData.name,
        firstName: userData.given_name,
        lastName: userData.family_name,
      };
      const token = sessionStorage.getItem("token");
      // Make POST API call to your backend with user data
      const url = import.meta.env.VITE_BACKEND_API_BASE_URL + "/user/";
      console.log("URL is" + url);
      const response = await axios.post(url, user, {
        headers: {
          Authorization: `Bearer ${token}`, // Add the Bearer token here
          "Content-Type": "application/json", // Optional: add the content type
        },
      });

      // Check the response status or message
      if (response.status === 200) {
        console.log("User created successfully", response.data);
        const newUser: UserResponseInfo = response.data;
        setUserResponse(newUser);
        sessionStorage.setItem("userId", response.data.id.toString());
      } else {
        console.error("Failed to create user", response.data);
        setError("Failed to create user");
      }
    } catch (error) {
      console.error("Error during API call", error);
      setError("Error while communicating with the backend");
    } finally {
      setLoading(false); // Stop loading when done
    }
  };

  return (
    <GoogleOAuthProvider clientId={clientId}>
      <div>
        <h1>Credentials Registration</h1>
        <hr />
        {loading && <p>Loading...</p>}

        {error && <p className="error">{error}</p>}
        {userResponse ? (
          <div>
            <h2>Welcome aboard, {userResponse.name}!!</h2>
            <hr />
            <OrganizationComponent user={userResponse} />
            <button onClick={handleLogout} className="btn btn-warning logout">
              Logout
            </button>
          </div>
        ) : (
          <GoogleLogin
            onSuccess={handleLoginSuccess}
            onError={() => {
              console.error("Login Failed");
              setError("Login failed");
            }}
          />
        )}
      </div>
    </GoogleOAuthProvider>
  );
};

export default App;
