import React, { useEffect, useState } from "react";
import AllCustomers from "../components/AllCustomers";
import Navbar from "./Navbar";

function AllCustomersPage() {
  const [searchQuery, setSearchQuery] = useState("");
  function islogedin() {
    const token = localStorage.getItem("authId");
    if (token != null) {
      return true;
    } else {
      return false;
    }
  }
  useEffect(() => {
    if (islogedin()) {
      console.log("Admin is logged in");
    } else {
      window.location.href = "/auth";
    }
  }, []);
  return (
    <>
      <Navbar onSearch={setSearchQuery} />
      <AllCustomers searchQuery={searchQuery} />
    </>
  );
}
export default AllCustomersPage;
