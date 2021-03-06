import React, { useState } from "react";
import "./PlasticReports.css";
import { useUserAuth } from "../../UserAuthContext";
import { Link } from "react-router-dom";
import { ref, remove } from "firebase/database";
import LocalShippingIcon from "@mui/icons-material/LocalShipping";
import {
  DataGrid,
  GridToolbarContainer,
  GridToolbarExport,
  GridToolbarDensitySelector,
} from "@mui/x-data-grid";
import { useNavigate } from "react-router-dom";
import { db } from "../../Firebase";
import { confirm } from "react-confirm-box";

import DeleteIcon from "@mui/icons-material/Delete";

function CustomToolbar() {
  return (
    <GridToolbarContainer>
      <GridToolbarDensitySelector />
      <GridToolbarExport
        csvOptions={{
          fileName: "PlasticReports",
        }}
        printOptions={{
          hideFooter: true,
          hideToolbar: true,
        }}
      />
    </GridToolbarContainer>
  );
}

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

  const onHandleTobeConfirm = () => {
    navigate("/plasticConfirm");
  };

  const columns = [
    {
      field: `contributionID`,
      title: "contributionID",
      headerName: "Contribution ID",
      width: 200,
    },

    {
      field: "fullname",
      title: "Fullname",
      headerName: "Fullname",
      width: 200,
    },
    { field: "address", title: "Address", headerName: "Address", width: 200 },
    {
      field: "longandlat",
      title: "Longitude and Lat",
      headerName: "Longitude and Latitude",
      width: 300,
    },
    { field: "number", title: "Number", headerName: "Number", width: 200 },
    { field: "time", title: "Time", headerName: "Time", width: 100 },

    {
      field: "action",
      headerName: "Action",
      title: null,
      title: "action ",
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
      <div className="DelieveryContainer">
        <button className="DelieveryButton" onClick={onHandleTobeConfirm}>
          <LocalShippingIcon />
          Delivery
        </button>
      </div>
      <DataGrid
        title="Employee Data"
        className="dataGrid"
        getRowId={(row) => row.contributionID}
        style={{ height: "60%", width: "100%" }}
        disableSelectionOnClick
        rows={RealtimeData}
        columns={columns}
        pageSize={10}
        onSelectionModelChange={(rows) => setSelectedRows(rows)}
        rowsPerPageOptions={[10]}
        components={{ Toolbar: CustomToolbar }}
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
