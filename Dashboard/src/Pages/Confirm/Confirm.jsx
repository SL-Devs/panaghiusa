import React, { useEffect, useState } from "react";
import "./Confirm.css";
import { db } from "../../Firebase";

import { set, ref, onValue, remove, update } from "firebase/database";

import { CalendarToday } from "@material-ui/icons";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
import NumbersIcon from "@mui/icons-material/Numbers";
import HomeIcon from "@mui/icons-material/Home";
import CallIcon from "@mui/icons-material/Call";
import BadgeIcon from "@mui/icons-material/Badge";
import { useParams, useNavigate } from "react-router-dom";
import { confirm } from "react-confirm-box";

export default function Confirm() {
  const { confirmId } = useParams();
  const [DataFilter, setDataFilter] = useState([]);
  const [contributionid, setContributionID] = useState("");
  const [address, setAddress] = useState("");
  const [date, setDate] = useState("");
  const [fullname, setFullname] = useState("");
  const [itemNo, setItemNo] = useState("");
  const [number, setNumber] = useState("");
  const [longandlat, setLongandlat] = useState("");
  const [plasticURL, setPlasticURL] = useState("");
  const [userId, setUserId] = useState("");
  const [contribution, setContribution] = useState("");
  const formatAMPM = (date) => {
    let hours = date.getHours();
    let minutes = date.getMinutes();
    const ampm = hours >= 12 ? "PM" : "AM";
    hours %= 12;
    hours = hours || 12;
    minutes = minutes < 10 ? `0${minutes}` : minutes;
    const strTime = `${hours}:${minutes} ${ampm}`;
    return strTime;
  };

  useEffect(() => {
    setDataFilter([]);
    onValue(ref(db, `TBC_PlasticAll/${confirmId}`), (snapshot) => {
      const data = snapshot.val();
      setDataFilter(data);
      setContributionID(data.contributionid);
      setUserId(data.userid);
      setFullname(data.fullname);
      setItemNo(data.itemNo);
      setNumber(data.number);
      setLongandlat(data.longandlat);
      setPlasticURL(data.plasticURL);
      setDate(data.date);
      setContribution(data.contribution);
      setAddress(data.address);
    });
  }, []);

  const TBC_PlasticSpecific = () => {
    remove(ref(db, `TBC_PlasticAll/${confirmId}`));
    remove(ref(db, `TBC_PlasticSpecific/${userId}/${confirmId}`));
  };
  const WriteToDataBase = async () => {
    const result = await confirm("Are you sure to confirm this delievery?");
    if (result) {
      set(ref(db, `TBP_PlasticSpecific/${userId}/${confirmId}`), {
        contributionid,
        fullname,
        address,
        itemNo,
        plasticURL,
        userId,
        date,
        time: formatAMPM(new Date()),
        longandlat,
        contribution,
        number,
      });
      set(ref(db, `TBP_PlasticAll/${confirmId}`), {
        contributionid,
        fullname,
        address,
        itemNo,
        plasticURL,
        userId,
        date,
        time: formatAMPM(new Date()),
        longandlat,
        contribution,
        number,
      });
      Navigate("/plasticConfirm");
      TBC_PlasticSpecific();
    }
  };
  const Navigate = useNavigate();

  const buttonPrev = () => {
    Navigate("/plasticConfirm");
  };

  return (
    <div className="Confirm">
      <div className="user">
        <div className="userTitleContainerPlastic">
          <h1 className="ConfirmDelievery">Confirm Delivery </h1>
        </div>
        <div className="userTitleContainer">
          <button onClick={buttonPrev} className="prevBtn">
            Previous
          </button>
        </div>
        <div className="userContainer">
          <div className="ShowUserInfo">
            <div className="userShowTop">
              <img
                src="
              https://firebasestorage.googleapis.com/v0/b/panaghiusa-28480.appspot.com/o/ProfilePicture%2Fo5TG4mhWscfMbsL0gid9hn8jABd2user_profile.png?alt=media&token=1d77c8bd-6879-46de-b929-dabded01a631"
                alt=""
                className="userShowImg"
              />
              <div className="userShowTopTitle">
                <span className="userShowUsername">{DataFilter.fullname}</span>
              </div>
            </div>
            <div className="userShowBottom">
              <span className="userShowTitle">Account Details</span>
              <div className="userShowInfo">
                <NumbersIcon className="userShowIcon" />
                <span className="userShowInfoTitle">
                  Item No: {DataFilter.itemNo}{" "}
                </span>
              </div>

              <div className="userShowInfo">
                <BadgeIcon className="userShowIcon" />
                <span className="userShowInfoTitle">
                  {DataFilter.contributionid}
                </span>
              </div>

              <span className="userShowTitle">Contact Details</span>
              <div className="userShowInfo">
                <CallIcon className="userShowIcon" />
                <span className="userShowInfoTitle">{DataFilter.number}</span>
              </div>
              <div className="userShowInfo">
                <HomeIcon className="userShowIcon" />
                <span className="userShowInfoTitle">{DataFilter.address}</span>
              </div>
              <div className="userShowInfo">
                <LocationOnIcon className="userShowIcon" />
                <span className="userShowInfoTitle">
                  {DataFilter.longandlat}
                </span>
              </div>
              <div className="userShowInfo">
                <AccessTimeIcon className="userShowIcon" />
                <span className="userShowInfoTitle">{DataFilter.time}</span>
              </div>
              <div className="userShowInfo">
                <CalendarToday className="userShowIcon" />
                <span className="userShowInfoTitle">{DataFilter.date}</span>
              </div>
              <div className="ConfirmButton">
                <button onClick={WriteToDataBase} className="btnTBP">
                  {" "}
                  Confirm to TBP{" "}
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
