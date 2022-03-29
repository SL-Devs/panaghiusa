import React, { useState, useEffect } from "react";
import "./OrganicWasteDelivery.css";
import { db } from "../../Firebase";
import DeleteIcon from "@mui/icons-material/Delete";
import { confirm } from "react-confirm-box";

import {
  DataGrid,
  GridToolbarContainer,
  GridToolbarExport,
  GridToolbarDensitySelector,
} from "@mui/x-data-grid";
import { Link, useNavigate } from "react-router-dom";
import { set, ref, onValue, remove, update } from "firebase/database";

function CustomToolbar() {
  return (
    <GridToolbarContainer>
      <GridToolbarDensitySelector />
      <GridToolbarExport />
    </GridToolbarContainer>
  );
}
export default function ToBeConfirm() {
  const navigate = useNavigate();
  const [ToBeConfirm, setToBeConfirm] = useState([]);

  useEffect(() => {
    onValue(ref(db, "TBC_OrganicAll/"), (snapshot) => {
      setToBeConfirm([]);
      const data = snapshot.val();
      if (data !== null) {
        Object.values(data).map((todo) => {
          setToBeConfirm((oldArray) => [...oldArray, todo]);
        });
      }
    });
  }, []);

  console.log(ToBeConfirm);

  const buttonPrev = () => {
    navigate("/wastemanagement");
  };

  const handleDeleteSelectedRows = async () => {
    const result = await confirm("Are you sure you to get rid of this data ?");
    if (result) {
      setToBeConfirm(
        ToBeConfirm.filter((row) => !selectedRows.includes(row.id))
      );
      selectedRows.map((item) => {
        return remove(ref(db, `TBC_PlasticAll/${item}`));
      });
    }
  };

  const RealtimeDelete = async (id) => {
    const result = await confirm("Are you sure you to get rid of this data ?");
    if (result) {
      setToBeConfirm(ToBeConfirm.filter((item) => item.contributionid !== id));
      remove(ref(db, `TBC_PlasticAll/${id}`));
      console.log(id);
    }
  };
  const columns = [
    { field: `contributionid`, headerName: "Contribution ID", width: 200 },
    { field: "fullname", headerName: "Fullname", width: 200 },
    { field: "address", headerName: "Address", width: 200 },
    { field: "longandlat", headerName: "Longitude and Latitude", width: 300 },
    { field: "number", headerName: "Number", width: 200 },
    { field: "time", headerName: "Time", width: 100 },

    {
      field: "action",
      headerName: "Action",
      width: 200,
      renderCell: (params) => {
        return (
          <>
            <Link to={"/organicwasteconfirm/" + params.row.contributionid}>
              <button className="userListEdit">Confirm</button>
            </Link>
            {
              <DeleteIcon
                className="userListDelete"
                onClick={() => RealtimeDelete(params.row.contributionid)}
              />
            }
          </>
        );
      },
    },
  ];

  return (
    <div className="userList">
      <div className="reportTitle">Organic Waste Delivery Confirmation</div>

      <DataGrid
        getRowId={(row) => row.contributionid}
        className="dataGrid"
        style={{ height: "60%", width: "100%" }}
        disableSelectionOnClick
        rows={ToBeConfirm}
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
        <div>
          <button className="buttonBack" onClick={buttonPrev}>
            Previous
          </button>
        </div>
      </div>
    </div>
  );
}
