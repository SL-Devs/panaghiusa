import React, { useState, useEffect } from "react";
import {
  CalendarToday,
  MailOutline,
  PermIdentity,
  PhoneAndroid,
  LocationCity,
} from "@material-ui/icons";
import SynagogueIcon from "@mui/icons-material/Synagogue";

import { db } from "../../Firebase";
import { Link, useParams } from "react-router-dom";
import { ref, onValue } from "firebase/database";
import "./User.css";

export default function User() {
  const { userId } = useParams();

  const [dataFilter, setDataFilter] = useState([]);
  useEffect(() => {
    onValue(ref(db, `Users/` + userId), (snapshot) => {
      setDataFilter([]);
      const data = snapshot.val();
      setDataFilter(data);
    });
  }, []);
  console.log(dataFilter);

  return (
    <div className="user">
      <div className="ViewUser">
        <h1 className="userTitle">View User</h1>
      </div>
      <div className="userTitleContainer">
        <Link to="/users">
          <button className="userAddButton">Previous</button>
        </Link>
      </div>
      <div className="userContainer">
        <div className="userShow">
          <div className="userShowTop">
            <img src={dataFilter.profileLink} alt="" className="userShowImg" />
            <div className="userShowTopTitle">
              <span className="userShowUsername">{dataFilter.fullname}</span>
            </div>
          </div>
          <div className="userShowBottom">
            <span className="userShowTitle">User Details </span>
            <div className="userShowInfo">
              <PermIdentity className="userShowIcon" />
              <span className="userShowInfoTitle">{dataFilter.id}</span>
            </div>
            <div className="userShowInfo">
              <CalendarToday className="userShowIcon" />
              <span className="userShowInfoTitle">
                Date Created: <strong>{dataFilter.date}</strong>
              </span>
            </div>

            <div className="userShowInfo">
              <PhoneAndroid className="userShowIcon" />
              <span className="userShowInfoTitle">{dataFilter.number}</span>
            </div>
            <div className="userShowInfo">
              <MailOutline className="userShowIcon" />
              <span className="userShowInfoTitle">{dataFilter.email}</span>
            </div>
            <div className="userShowInfo">
              <SynagogueIcon className="userShowIcon" />
              <span className="userShowInfoTitle">{dataFilter.barangay}</span>
            </div>

            <div className="userShowInfo">
              <LocationCity className="userShowIcon" />
              <span className="userShowInfoTitle">{dataFilter.city}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
