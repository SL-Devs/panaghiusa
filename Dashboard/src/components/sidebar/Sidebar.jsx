import React from "react";
import "./Sidebar.css";
import { Link } from "react-router-dom";
import LineStyleIcon from "@mui/icons-material/LineStyle";
import FileUploadIcon from "@mui/icons-material/FileUpload";
import PersonIcon from "@mui/icons-material/Person";
import AssessmentIcon from "@mui/icons-material/Assessment";

export default function Sidebar() {
  return (
    <div className="sidebar">
      <div className="sidebarWrapper">
        <div className="sidebarMenu">
          <h3 className="sidebarTitle">Dashboard</h3>
          <ul className="sidebarList">
            <div className="icon-wrapper">
              <Link to="/home">
                <li className="sidebarListItem ">
                  <LineStyleIcon className="sideBarIcon" /> Home
                </li>
              </Link>

              <Link to="/realtime">
                <li className="sidebarListItem">
                  <AssessmentIcon className="sideBarIcon" /> Reports
                </li>
              </Link>
              <Link to="/users">
                <li className="sidebarListItem ">
                  <PersonIcon className="sideBarIcon" /> User
                </li>
              </Link>
              <Link to="/upload">
                <li className="sidebarListItem ">
                  <FileUploadIcon className="sideBarIcon" /> Upload Image
                </li>
              </Link>
            </div>
          </ul>
        </div>
      </div>
    </div>
  );
}
