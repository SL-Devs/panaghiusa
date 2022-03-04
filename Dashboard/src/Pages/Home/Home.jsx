import React, { useState, useEffect } from "react";
import "./Home.css";
import FeatureInfo from "../../components/FeatureInfo/FeatureInfo";
import Charts from "../../components/Chart/Charts";
import WidgetLarge from "../../components/WidgetLarge/WidgetLarge";
import WidgetSmall from "../../components/WidgetSmall/WidgetSmall";
import { Alert, Icon } from "@mui/material";
import { fontSize } from "@mui/system";

export default function Home() {
  return (
    <div className="home">
      <Charts />

      <div className="homeWidgets">
        <WidgetSmall />
        <WidgetLarge />
      </div>
    </div>
  );
}
