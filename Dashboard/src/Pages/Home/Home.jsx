import React from 'react'
import './Home.css'
import FeatureInfo from '../../components/FeatureInfo/FeatureInfo'
import Charts from '../../components/Chart/Charts'
import WidgetLarge from '../../components/WidgetLarge/WidgetLarge'
import WidgetSmall from '../../components/WidgetSmall/WidgetSmall'


export default function Home() {
  return (
    <div className='home'>
        <Charts />
       
      <div className="homeWidgets">
        <WidgetSmall />
        <WidgetLarge />
        </div> 
 
    </div>
  )
}
