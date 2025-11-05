import axios from "../api/backendAPI";
import React, { useState } from "react";
import Navbar from "./Navbar";
import UploadingCustomers from "../components/UploadingCustomers";
import { Link } from "react-router-dom";

function AdminPanel() {

  return (
    <>
      <Navbar />
      <section className="bg-blue-100 py-20 text-center">
        <div className="container mx-auto px-4">
          <h1 className="text-4xl font-bold text-blue-800 mb-4">
            Welcome to Admin Panel.
          </h1>
          <p className="text-lg text-blue-700 mb-6">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Quis culpa
            recusandae, iure fugit ducimus est?
          </p>
          <Link
            to={{ pathname: "/signup" }}
            className="bg-blue-700 text-white px-6 py-2 rounded hover:bg-blue-800"
          >
            Create Customer!
          </Link>
        </div>
      </section>
      <div className="flex flex-row m-4">
        <Link to={{ pathname: "/uploadCustomers" }} className="basis-1/2 m-2 p-2 h-50 bg-blue-600/100 rounded-md">
          <div ><p className="text-xl text-center font-bold text-white">Upload Customers</p></div>
        </Link>

        <Link to={{pathname: "/allCustomers"}} className="basis-1/2 m-2 p-2 h-50 bg-blue-600/100 rounded-md">
          <div><p className="text-xl text-center font-bold text-white">Customers List</p></div>
        </Link>
      </div>
    </>
  );
}

export default AdminPanel;
