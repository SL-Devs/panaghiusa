import "./app.css";
import Sidebar from "./components/sidebar/Sidebar";
import Topbar from "./components/topbar/Topbar";
import Home from "./Pages/Home/Home";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import UserList from "./Pages/UserList/UserList";
import User from "./Pages/User/User";
import Realtime from "./Pages/Plastic/Plastic";
import PlasticReports from "./Pages/PlasticReports/PlasticReports";
import ImageUpload from "./Pages/ImageUpload/ImageUpload";
import Waste from "./Pages/Waste/Waste";
import WasteManagement from "./Pages/WasteManagement/WasteManagement";
import { useState } from "react";
import Login from "./Pages/Login/Login";
import { useUserAuth } from "./UserAuthContext";

function App() {
  const { logout, setLogout } = useUserAuth();
  return (
    <Router>
      <Routes>
        <Route
          exact
          path="/"
          element={<Login logout={logout} setLogout={setLogout} />}
        />
      </Routes>
      {logout && (
        <>
          <Topbar />
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
            </Routes>
          </div>
        </>
      )}
    </Router>
  );
}

export default App;
