import React from 'react'
import './WidgetSmall.css'
import VisibilityIcon  from '@mui/icons-material/Visibility';
export default function WidgetSmall() {
  return (
    <div className='widgetSm'>
      <span className="widgetSmTitle">New Join Members</span>
      <ul className="widgetSmList">
       
        <li className="widgetSmListItem">
          <img src="https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80" alt="" className="widgetSmImg" />
          <div className="widgetSmUser">
            <span className="widgetSmUserName">Jaycon</span>
            <span className="widgetSmUserTitle">Softwate</span>
          </div>
          <button className="widgetSmButton">
            <VisibilityIcon className="widgetSmIcon" /> 
            Display
            </button>
        </li>
        <li className="widgetSmListItem">
          <img src="https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80" alt="" className="widgetSmImg" />
          <div className="widgetSmUser">
            <span className="widgetSmUserName">Jaycon</span>
            <span className="widgetSmUserTitle">Softwate</span>
          </div>
          <button className="widgetSmButton">
            <VisibilityIcon className="widgetSmIcon" /> 
            Display
            </button>
        </li>
      </ul>
    </div>
  )
}
