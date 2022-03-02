import React, { useState } from "react";
import "./Login.css";
import { Link, useNavigate } from "react-router-dom";
import { useUserAuth } from "../../UserAuthContext";
import { set } from "firebase/database";
import { Alert, TextField, Button, FormControl } from "@mui/material";
export default function Login({ setLogout }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const { logIn } = useUserAuth();
  const Navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(" ");

    try {
      await logIn(email, password);
      Navigate("/home");
      setLogout(true);
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="login">
      <div className="imgContainer">
        <img
          className="logoLogin"
          src="https://panaghiusa.netlify.app/img/panaghiusa.png"
        />
        <h1>Panaghiusa</h1>
      </div>

      <div className="parentContainer">
        <div className="dashboardtext">
          <h1>Dashboard</h1>
        </div>
        <hr />
        <form onSubmit={handleSubmit}>
          {error ? (
            <Alert sx={{ color: "red", marginBottom: "20px" }} severity="error">
              {error}
            </Alert>
          ) : null}
          <div className="Textfields">
            <input
              className="inputs"
              placeholder="Email Address"
              variant="standard"
              type="text"
              required
              onChange={(e) => setEmail(e.target.value)}
            />

            <input
              className="inputs"
              placeholder="Password"
              variant="standard"
              type="password"
              required
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          <button className="login-button">Login</button>
        </form>
      </div>
    </div>
  );
}
