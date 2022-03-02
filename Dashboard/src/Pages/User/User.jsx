import React, { useState, useEffect } from "react";
import {
  CalendarToday,
  LocationSearching,
  MailOutline,
  PermIdentity,
  PhoneAndroid,
} from "@material-ui/icons";
import { db } from "../../Firebase";
import { Link, useParams } from "react-router-dom";
import { set, ref, onValue, update } from "firebase/database";
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
            <img
              src="https://images.pexels.com/photos/1152994/pexels-photo-1152994.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
              alt=""
              className="userShowImg"
            />
            <div className="userShowTopTitle">
              <span className="userShowUsername">{dataFilter.fullname}</span>
            </div>
          </div>
          <div className="userShowBottom">
            <span className="userShowTitle">User Details </span>
            <div className="userShowInfo">
              <PermIdentity className="userShowIcon" />
              <span className="userShowInfoTitle">{dataFilter.number}</span>
            </div>
            <div className="userShowInfo">
              <CalendarToday className="userShowIcon" />
              <span className="userShowInfoTitle">{dataFilter.date}</span>
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
              <LocationSearching className="userShowIcon" />
              <span className="userShowInfoTitle">{dataFilter.address}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
