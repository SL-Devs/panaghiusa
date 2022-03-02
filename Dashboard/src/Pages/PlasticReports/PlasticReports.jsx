import React, { useState } from "react";
import "./PlasticReports.css";
import { useUserAuth } from "../../UserAuthContext";
import { Link } from "react-router-dom";
import { ref, remove } from "firebase/database";
import { DataGrid } from "@mui/x-data-grid";
import { useNavigate } from "react-router-dom";
import { db } from "../../Firebase";
import { confirm } from "react-confirm-box";
import DeleteIcon from "@mui/icons-material/Delete";

export default function RealtimeReports() {
  const navigate = useNavigate();

  const { RealtimeData, setRealtimeData } = useUserAuth();

  const [selectedRows, setSelectedRows] = useState([]);

  const RealtimeDelete = async (id) => {
    const result = await confirm("Are you sure you to get rid of this data ?");
    if (result) {
      setRealtimeData(
        RealtimeData.filter((item) => item.contributionID !== id)
      );
      remove(ref(db, `PlasticContribution/${id}`));
      console.log(id);
    }
  };

  const options = {
    labels: {
      confirmable: "Confirm",
      cancellable: "Cancel",
    },
  };
  const handleDeleteSelectedRows = async () => {
    const result = await confirm(
      "Are you sure you to get rid of this row data ?",
      options
    );
    if (result) {
      setRealtimeData(
        RealtimeData.filter((row) => !selectedRows.includes(row.contributionID))
      );
      selectedRows.map((item) => {
        remove(ref(db, `PlasticContribution/${item}`));
      });
    }
  };

  const nextPage = () => {
    navigate("/wastemanagement");
  };
  const columns = [
    { field: `contributionID`, headerName: "Contribution ID", width: 200 },
    {
      field: "value",
      headerName: "Profile ",
      width: 100,
      renderCell: (params) => {
        return (
          <>
            <img src={params.row.value} alt="" width="30px" height="30px" />
          </>
        );
      },
    },
    { field: "fullname", headerName: "Fullname", width: 200 },
    { field: "address", headerName: "Address", width: 200 },
    { field: "longandlat", headerName: "Longitude and Latitude", width: 300 },
    { field: "number", headerName: "Number", width: 200 },
    { field: "time", headerName: "Time", width: 100 },

    {
      field: "action",
      headerName: "Action",
      width: 100,
      renderCell: (params) => {
        return (
          <>
            <Link to={"/realtimeone/" + params.row.contributionID}>
              <button className="userListEdit">Edit</button>
            </Link>
            {
              <DeleteIcon
                className="userListDelete"
                onClick={() => RealtimeDelete(params.row.contributionID)}
              />
            }
          </>
        );
      },
    },
  ];
  return (
    <div className="userList">
      <div className="reportTitle">Plastic Contribution Reports</div>

      <DataGrid
        className="dataGrid"
        getRowId={(row) => row.contributionID}
        style={{ height: "60%", width: "100%" }}
        disableSelectionOnClick
        rows={RealtimeData}
        columns={columns}
        pageSize={10}
        onSelectionModelChange={(rows) => setSelectedRows(rows)}
        rowsPerPageOptions={[10]}
        checkboxSelection
      />
      <div className="buttonChanges">
        <button className="buttonDelete" onClick={handleDeleteSelectedRows}>
          {" "}
          Delete
        </button>
        <button className="buttonNext" onClick={nextPage}>
          Next
        </button>
      </div>
    </div>
  );
}
