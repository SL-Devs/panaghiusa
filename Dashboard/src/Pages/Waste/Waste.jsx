import React, { useEffect, useState } from "react";
import "./Waste.css";
import Alert from "@mui/material/Alert";
import Tooltip from "@mui/material/Tooltip";
import {
  CalendarToday,
  LocationSearching,
  PhoneAndroid,
  AccessTime,
  AddLocation,
} from "@material-ui/icons";
import NumbersIcon from "@mui/icons-material/Numbers";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import { db } from "../../Firebase";
import Snackbar from "@mui/material/Snackbar";
import { ref, onValue, update } from "firebase/database";

export default function User() {
  const { organicwasteId } = useParams();
  const [dataFilter, setDataFilter] = useState([]);
  const [time, setTime] = useState("");
  const [fullname, setFullname] = useState(" ");
  const [address, setAddress] = useState("");
  const [number, setNumber] = useState("");
  const [longandlat, setLongandlat] = useState("");

  const [state, setState] = React.useState({
    open: false,
    vertical: "top",
    horizontal: "center",
  });

  const { vertical, horizontal, open } = state;

  const handleClick = (newState) => () => {
    setState({ open: true, ...newState });
  };

  const handleClose = () => {
    setState({ ...state, open: false });
  };
  useEffect(() => {
    onValue(ref(db, `OrganicContribution/${organicwasteId}`), (snapshot) => {
      setDataFilter([]);
      const data = snapshot.val();
      setDataFilter(data);
      setFullname(data.fullname);
      setAddress(data.address);
      setNumber(data.number);
      setTime(data.time);
      setLongandlat(data.longandlat);
    });
  }, []);

  const handleSubmitChange = (e) => {
    e.preventDefault();
    update(ref(db, `OrganicContribution/${organicwasteId}`), {
      fullname,
      time,
      address,
      number,
      longandlat,
    });
  };

  return (
    <div className="user">
      <Snackbar
        anchorOrigin={{ vertical, horizontal }}
        open={open}
        onClose={handleClose}
        key={vertical + horizontal}
        autoHideDuration={3000}
      >
        <Alert
          sx={{
            width: "200px",
            fontSize: "13px",
            justifyContent: "center",
            color: "white",
            backgroundColor: "#4C9865",
          }}
          severity="success"
        >
          Updated Successfully!
        </Alert>
      </Snackbar>
      <div className="userTitleContainerPlastic">
        <h1 className="userTitle">EDIT USER</h1>
        <Link to="/realtime">
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
            <span className="userShowTitle">Account Details</span>
            <div className="userShowInfo">
              <Tooltip title="Contribution ID">
                <NumbersIcon className="userShowIcon" />
              </Tooltip>

              <span className="userShowInfoTitle">{dataFilter.number}</span>
            </div>

            <div className="userShowInfo">
              <Tooltip title="Date">
                <CalendarToday className="userShowIcon" />
              </Tooltip>
              <span className="userShowInfoTitle">{dataFilter.date}</span>
            </div>
            <span className="userShowTitle">Contact Details</span>
            <div className="userShowInfo">
              <Tooltip title="Phone Number">
                <PhoneAndroid className="userShowIcon" />
              </Tooltip>
              <span className="userShowInfoTitle">{dataFilter.number}</span>
            </div>
            <div className="userShowInfo">
              <Tooltip title="Time">
                <AccessTime className="userShowIcon" />
              </Tooltip>
              <span className="userShowInfoTitle">{dataFilter.time}</span>
            </div>
            <div className="userShowInfo">
              <Tooltip title="Address">
                <AddLocation className="userShowIcon" />
              </Tooltip>
              <span className="userShowInfoTitle">{dataFilter.address}</span>
            </div>
            <div className="userShowInfo">
              <Tooltip title="Longitude and Latitude">
                <LocationSearching className="userShowIcon" />
              </Tooltip>
              <span className="userShowInfoTitle">{dataFilter.longandlat}</span>
            </div>
          </div>
        </div>
        <div className="userUpdate">
          <div className="alert"></div>
          <div className="formContainer">
            <span className="userUpdateTitle">Edit</span>
            <form onSubmit={handleSubmitChange} className="userUpdateForm">
              <div className="userUpdateLeft">
                <div className="userUpdateItem">
                  <label>Fullname</label>
                  <input
                    onChange={(e) => setFullname(e.target.value)}
                    type="text"
                    className="userUpdateInput"
                    value={fullname || " "}
                    required
                  />
                </div>
                <div className="userUpdateItem">
                  <label>Address</label>
                  <input
                    name="address"
                    type="text"
                    onChange={(e) => setAddress(e.target.value)}
                    className="userUpdateInput"
                    value={address || ""}
                    required
                  />
                </div>

                <div className="userUpdateItem">
                  <label>Longitude and Latitude</label>
                  <input
                    type="text"
                    onChange={(e) => setLongandlat(e.target.value)}
                    className="userUpdateInput"
                    value={longandlat || ""}
                    required
                  />
                </div>
                <div className="userUpdateItem">
                  <label>Number</label>
                  <input
                    type="text"
                    onChange={(e) => setNumber(e.target.value)}
                    className="userUpdateInput"
                    value={number || ""}
                    required
                  />
                </div>
                <div className="userUpdateItem">
                  <label>Time</label>
                  <input
                    type="time"
                    onChange={(e) => setTime(e.target.value)}
                    className="userUpdateInput"
                    value={time || ""}
                    required
                  />
                </div>
              </div>
              <div className="userUpdateRight">
                <button
                  onClick={handleClick({
                    vertical: "top",
                    horizontal: "center",
                  })}
                  className="userUpdateButton"
                >
                  Update
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
