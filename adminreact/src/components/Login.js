import React, { useState } from "react";
import './Login.css'
import { Link, useNavigate } from "react-router-dom";
import { Form, Alert } from "react-bootstrap";
import { Button } from "react-bootstrap";
//import GoogleButton from "react-google-button";
import { useUserAuth } from "../context/UserAuthContext";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const { logIn, googleSignIn } = useUserAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      await logIn(email, password);
      navigate("/home");
    } catch (err) {
      setError(err.message);
    }
  };

  /*const handleGoogleSignIn = async (e) => {
    e.preventDefault();
    try {
      await googleSignIn();
      navigate("/home");
    } catch (error) {
      console.log(error.message);
    }
  };*/

  return (
    <>
      <div className="container">
        <div className="parent-container">
        <h2 className="logInText">Login </h2>
        <h4 className="dashBoardText">Proceed to dashboard</h4>
        <hr />
        {error && <Alert variant="danger">{error}</Alert>}
        <Form onSubmit={handleSubmit}>
          <Form.Group className=" mb-3" controlId="formBasicEmail">
            <Form.Control
      className="input-one"
              type="email"
              placeholder="Email address . . ."
              onChange={(e) => setEmail(e.target.value)}
            />
          </Form.Group>

          <Form.Group className="password mb-3" controlId="formBasicPassword">
            <Form.Control
            className="input-two"
              type="password"
              placeholder="Password . . ."
              onChange={(e) => setPassword(e.target.value)}
            />
          </Form.Group>

          <div className="d-grid gap-2">
            <Button className="Button" variant="primary" type="Submit">
              Log in Admin
            </Button>
          </div>
        </Form>
  
     
      </div>
    {/* <div className="p-4 box mt-3 text-center">
    Don't have an account? <Link to="/signup">Sign up</Link>
      </div> */}
           </div>
    </>
  );
};

export default Login;