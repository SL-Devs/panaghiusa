import React from 'react'
import './FeatureInfo.css'
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward';
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';

export default function FeatureInfo() {
  return (
    <div className='featured'>
        <div className="featuredItem">
            <span className="featuredTitle">Sales</span>
            <div className="featuredMoneyContainer">
                <span className="span featuredMoney">P24,51</span>
                <span className="span featuredMoneyRate">+11.4 <ArrowUpwardIcon className='featuredIcon positive'/></span>
            </div>
            <span className="featuredSub">Compared to last month</span>
        </div>
        <div className="featuredItem">
            <span className="featuredTitle">Revenue</span>
            <div className="featuredMoneyContainer">
                <span className="span featuredMoney">P24,51</span>
                <span className="span featuredMoneyRate">-11.4 <ArrowDownwardIcon className='featuredIcon negative'/></span>
            </div>
            <span className="featuredSub">Compared to last month</span>
        </div>
        <div className="featuredItem">
            <span className="featuredTitle">Cost</span>
            <div className="featuredMoneyContainer">
                <span className="span featuredMoney">P24,51</span>
                <span className="span featuredMoneyRate">-11.4 <ArrowDownwardIcon className='featuredIcon'/></span>
            </div>
            <span className="featuredSub">Compared to last month</span>
        </div>
    </div>
  )
}
