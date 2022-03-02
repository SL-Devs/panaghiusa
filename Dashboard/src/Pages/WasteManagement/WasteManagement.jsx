import React, { useState } from 'react'
import "./WasteManagement.css"
import { useUserAuth } from '../../UserAuthContext';
import { Link, useNavigate } from 'react-router-dom'
import { ref, remove } from "firebase/database";
import { DataGrid } from '@mui/x-data-grid';
import { confirm } from "react-confirm-box";
import { db } from "../../Firebase";

import DeleteIcon from '@mui/icons-material/Delete';

export default function WasteManagement() {
  const navigate = useNavigate();
  const { setOrganicContribution, OrganicContribution } = useUserAuth();

  const [selectedRows, setSelectedRows] = useState([]);



  
  const RealtimeDelete = async(id) => {
    const result = await confirm("Are you sure you to get rid of this data ?");
    if (result) {
      setOrganicContribution(OrganicContribution.filter((item) => item.contributionID !== id));
    remove(ref(db, `PlasticContribution/${id}`))
    console.log(id)
    }
  };


  const handleDeleteSelectedRows = async () => {
    const result = await confirm("Are you sure you to get rid of this data ?");
    if (result) {
    setOrganicContribution(OrganicContribution.filter(row => !selectedRows.includes(row.id)))
    selectedRows.map((item) => {
      return (
        remove(ref(db, `Users/${item}`))
      )
    })
  }
  }

  const buttonPrev = () => {
    navigate("/realtime")

  }


  const columns = [

    { field: `contributionID`, headerName: 'Contribution ID', width: 200 },
    {
      field: 'value', headerName: 'Profile ', width: 100, renderCell: (params) => {
        return (
          <>
            <img src={params.row.value} alt='' width='30px' height='30px' />
          </>
        )
      }
    },
    { field: 'fullname', headerName: 'Fullname', width: 200 },
    { field: 'address', headerName: 'Address', width: 200 },
    { field: 'longandlat', headerName: 'Longitude and Latitude', width: 300 },
    { field: 'number', headerName: 'Number', width: 200 },
    { field: 'time', headerName: 'Time', width: 100 },


    {
      field: 'action',
      headerName: "Action",
      width: 100,
      renderCell: (params) => {
        return (
          <>
            <Link to={"/organicwaste/" + params.row.contributionID}>
              <button className="userListEdit">Edit</button>
            </Link>
            {<DeleteIcon className="userListDelete" onClick={() => RealtimeDelete(params.row.contributionID)} />}
          </>
        )
      }
    }
  ];
  return (
    <div className='userList'>
      <div className='reportTitle'>
        Organic Waste Contribution Reports   </div>

      <DataGrid
         getRowId={(row) => row.contributionID}
        className='dataGrid'
        style={{ height: '60%', width: '100%' }}
        disableSelectionOnClick
        rows={OrganicContribution}
        columns={columns}
        pageSize={10}
        onSelectionModelChange={(rows) => setSelectedRows(rows)}
        rowsPerPageOptions={[10]}
        checkboxSelection
      />


      <div className='buttonChanges'>
        <button className="buttonDelete" onClick={handleDeleteSelectedRows}> Delete</button>
        <div>


          <button className='buttonBack' onClick={buttonPrev}>Previous</button>

        </div>
      </div>

    </div>

  )
}
