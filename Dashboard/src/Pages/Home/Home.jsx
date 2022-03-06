import React, { useState, useEffect } from "react";
import "./Home.css";

import Charts from "../../components/Chart/Charts";
import WidgetLarge from "../../components/WidgetLarge/WidgetLarge";
import WidgetSmall from "../../components/WidgetSmall/WidgetSmall";

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
