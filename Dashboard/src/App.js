import "./app.css";
import Sidebar from "./components/sidebar/Sidebar";
import Topbar from "./components/topbar/Topbar";
import Home from "./Pages/Home/Home";
import { Navigate, Routes, Route } from "react-router-dom";
import UserList from "./Pages/UserList/UserList";
import User from "./Pages/User/User";
import Realtime from "./Pages/Plastic/Plastic";
import PlasticReports from "./Pages/PlasticReports/PlasticReports";
import ImageUpload from "./Pages/ImageUpload/ImageUpload";
import Waste from "./Pages/Waste/Waste";
import WasteManagement from "./Pages/WasteManagement/WasteManagement";
import { useState, useEffect } from "react";
import Login from "./Pages/Login/Login";

function App() {
  const [auth, setAuth] = useState(null);

  useEffect(() => {
    let user = localStorage.getItem("user");
    user && JSON.parse(user) ? setAuth(true) : setAuth(false);
  }, []);

  useEffect(() => {
    localStorage.setItem("user", auth);
  }, [auth]);

  return (
    <>
      {!auth && (
        <Routes>
          <Route
            exact
            path="/"
            element={
              <Login authenticate={() => setAuth(true)} setAuth={setAuth} />
            }
          />
        </Routes>
      )}

      {auth && (
        <>
          <Topbar setAuth={setAuth} />
          <div className="container">
            <Sidebar />

            <Routes>
              <Route path="/home" element={<Home />} />
              <Route path="/users" element={<UserList />} />
              <Route path="/user/:userId" element={<User />} />
              <Route path="/realtime" element={<PlasticReports />} />
              <Route path="/realtimeone/:realtimeId" element={<Realtime />} />
              <Route path="/wastemanagement" element={<WasteManagement />} />
              <Route path="/organicwaste/:organicwasteId" element={<Waste />} />
              <Route path="/upload" element={<ImageUpload />} />
              <Route
                path="*"
                element={<Navigate to={auth ? "/home" : "/"} />}
              />
            </Routes>
          </div>
        </>
      )}
    </>
  );
}

export default App;
