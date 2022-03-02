import React,{useState} from 'react'
import './WidgetLarge.css'
import { useUserAuth } from '../../UserAuthContext';  
import Box from '@mui/material/Box';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import SearchIcon from '@mui/icons-material/Search';
export default function WidgetLarge() {


  const {data} = useUserAuth() 
  console.table(data)
  const [searchTerm,setSearchTerm] = useState(" ")
  return (
    <div className='widgetLg'>
       <Box sx={{ display: 'flex', alignItems: 'flex-end',marginBottom:"20px" }}>
        <SearchIcon sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
        <TextField onChange={(e)=> setSearchTerm(e.target.value)} id="input-with-sx" label="Search . . ." variant="standard" />
      </Box>
      <TableContainer component={Paper} variant="outlined">
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
          <TableCell align="left"></TableCell>
            <TableCell align="left">ID</TableCell>
            <TableCell align="left"> Fullname </TableCell>
            <TableCell align="left"> Date </TableCell>
            <TableCell align="left">Email</TableCell>
            <TableCell align="left">Number</TableCell>
            <TableCell align="left">Points</TableCell>
            
          </TableRow>
        </TableHead>
        <TableBody>
       {data.filter((val)=>{
if(searchTerm === " "){
      return val;
    } else if (val.fullname.toLowerCase().includes(searchTerm.toLowerCase())){
      return val;
    } else if (val.email.toLowerCase().includes(searchTerm.toLowerCase())){
      return val
    }else if (val.number.toString().toLowerCase().includes(searchTerm.toString().toLowerCase())){
      return val
    }else if (val.id.toString().toLowerCase().includes(searchTerm.toString().toLowerCase())){
      return val
    }
       }).map((row)=>{
         return(
          <TableRow
          key={row.name}
          sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
        >
          <TableCell component="th" scope="row">
            {row.name}
          </TableCell>
          <TableCell align="left">{row.id}</TableCell>
          <TableCell align="left">{row.fullname}</TableCell>
          <TableCell align="left">{row.date}</TableCell>
          <TableCell align="left">{row.email}</TableCell>
          <TableCell align="left">{row.number}</TableCell>
          <TableCell align="left">{row.points}</TableCell>
        </TableRow>
         )
       })}
        </TableBody>
      </Table>
    </TableContainer>
    </div>
  )
}
