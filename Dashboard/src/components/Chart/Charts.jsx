import React, { PureComponent } from "react";
import "./Charts.css";
import {
  LineChart,
  Line,
  XAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
  Legend,
} from "recharts";

export default function Charts() {
  const userData = [
    {
      name: "Acrylic or Polymethyl Methacrylate (PMMA)",
      "Active Plastic": 4000,
      pv: 2400,
      amt: 2400,
    },
    {
      name: "Polycarbonate (PC)",
      "Active Plastic": 3000,
      pv: 1398,
      amt: 2210,
    },
    {
      name: "Polyethylene (PE)",
      "Active Plastic": 2000,
      pv: 9800,
      amt: 2290,
    },
    {
      name: "Polyethylene Terephthalate (PETE or PET)",
      "Active Plastic": 2780,
      pv: 3908,
      amt: 2000,
    },
    {
      name: "Polyvinyl Chloride (PVC)",
      "Active Plastic": 1890,
      pv: 4800,
      amt: 2181,
    },
    {
      name: "Polyvinyl Chloride (PVC)",
      "Active Plastic": 2390,
      pv: 3800,
      amt: 2500,
    },
    {
      name: " Acrylonitrile-Butadiene-Styrene (ABS)",
      "Active Plastic": 1000,
      pv: 3000,
      amt: 1000,
    },
  ];

  return (
    <div className="chart">
      <h3 className="chartTitle">Graph</h3>
      <ResponsiveContainer width="100%" aspect={4 / 1}>
        <LineChart data={userData}>
          <XAxis dataKey="name" stroke="#5550bd" />
          <Line type="monotone" dataKey="pv" stroke="green" />
          <Line type="monotone" dataKey="amt" stroke="blue" />
          <Line type="monotone" dataKey="Active Plastic" stroke="#5550bd" />
          <Tooltip />

          <CartesianGrid stroke="#e0dfdf" strokeDasharray="5 5" />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}
