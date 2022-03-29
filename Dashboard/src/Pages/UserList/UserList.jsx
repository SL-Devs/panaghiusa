import React, { useState } from "react";
import "./UserList.css";
import { Link } from "react-router-dom";
import { DataGrid } from "@mui/x-data-grid";
import DeleteIcon from "@mui/icons-material/Delete";
import { ref, remove } from "firebase/database";
import { useUserAuth } from "../../UserAuthContext";
import { db } from "../../Firebase";
import { confirm } from "react-confirm-box";

export default function UserList() {
  const { data, setData } = useUserAuth();
  const [selectedRows, setSelectedRows] = useState([]);

  const options = {
    labels: {
      confirmable: "Confirm",
      cancellable: "Cancel",
    },
  };

  const RealtimeDelete = async (id) => {
    const result = await confirm("Are you sure you to get rid of this data ?");
    if (result) {
      setData(data.filter((item) => item.id !== id));
      remove(ref(db, `Users/${id}`));
      console.log(id);
    }
  };

  const handleDeleteSelectedRows = async () => {
    const result = await confirm(
      "Are you sure you to get rid of this row data ?",
      options
    );
    if (result) {
      setData(data.filter((row) => !selectedRows.includes(row.id)));
      selectedRows.map((item) => {
        remove(ref(db, `Users/${item}`));
      });
    }
    return;
  };

  const columns = [
    { field: "id", headerName: "Report ID No. ", width: 100 },
    { field: "fullname", headerName: "Full Name", width: 200 },
    {
      field: "profileLink",
      headerName: "Profile ",
      width: 200,
      renderCell: (params) => {
        return (
          <>
            <img
              src={params.row.profileLink}
              alt=""
              width="30px"
              height="30px"
            />
          </>
        );
      },
    },
    { field: "email", headerName: "Email", width: 200 },
    { field: "number", headerName: "Number", width: 200 },
    { field: "city", headerName: "City", width: 200 },
    { field: "points", headerName: "Points", width: 100 },
    {
      field: "action",
      headerName: "Action",
      width: 150,
      renderCell: (params) => {
        return (
          <>
            <Link to={"/user/" + params.row.id}>
              <button className="userListEdit">View</button>
            </Link>
            {
              <DeleteIcon
                className="userListDelete"
                onClick={() => RealtimeDelete(params.row.id)}
              />
            }
          </>
        );
      },
    },
  ];
  return (
    <div className="userList">
      <div className="reportTitle">User details</div>

      <DataGrid
        style={{ height: "60%", width: "99%" }}
        disableSelectionOnClick
        rows={data}
        columns={columns}
        pageSize={10}
        rowsPerPageOptions={[5]}
        onSelectionModelChange={(rows) => setSelectedRows(rows)}
        checkboxSelection
      />
      <button className="buttonDelete" onClick={handleDeleteSelectedRows}>
        {" "}
        Delete
      </button>
    </div>
  );
}
