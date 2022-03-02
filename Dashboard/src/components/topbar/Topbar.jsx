import React from "react";
import "./topbar.css";
import ExitToAppIcon from "@mui/icons-material/ExitToApp";
import { useUserAuth } from "../../UserAuthContext";
import { Link, useNavigate } from "react-router-dom";

export default function Topbar() {
  const Navigate = useNavigate();
  const { logout, setLogout } = useUserAuth();

  const handleLogout = () => {
    setLogout(false);
    Navigate("/");
  };
  return (
    <div className="topbar">
      <div className="topbarWrapper">
        <div className="topLeft">
          <Link to="/home">
            <span className="logo">
              <img
                src="https://panaghiusa.netlify.app/img/panaghiusa.png"
                alt-=""
                width="100px"
                height="100px"
              />
              Panaghiusa
            </span>
          </Link>
        </div>
        <button onClick={handleLogout} className="button-logout">
          Log out <ExitToAppIcon className="iconExit" />{" "}
        </button>
      </div>
    </div>
  );
}