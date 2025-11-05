import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./Navbar.css";
import axios from "../api/backendAPI";

export default function Navbar({ data }) {
  const islogedin = () => {
    if (localStorage.getItem("custId") != null) {
      return true;
    } else {
      return false;
    }
  };

  const logout = () => {
    localStorage.removeItem("custId");
    window.location.reload();
  };

  const downloadPdf = async () => {
  const customerId = localStorage.getItem("custId"); // or pass dynamically
  if (!customerId) {
    alert("Customer ID not found!");
    return;
  }

  try {
    const response = await axios.get(`/customer/pdf/${customerId}`, {
      responseType: "blob", // IMPORTANT: ensures PDF is treated as binary
      headers: {
        "Accept": "application/pdf",
      },
    });

    // Create a blob from the response
    const blob = new Blob([response.data], { type: "application/pdf" });
    const url = window.URL.createObjectURL(blob);

    // Create a link and trigger download
    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", `customer_${customerId}.pdf`);
    document.body.appendChild(link);
    link.click();
    link.remove();

    // Optional: cleanup
    window.URL.revokeObjectURL(url);

  } catch (error) {
    console.error("Error downloading PDF:", error);
    alert("Failed to download PDF. Please try again.");
  }
};

  return (
    <nav className="navbar">
      <Link to={"/"} href="#" className="logo">
        Digi Wallet
      </Link>
      <div className="nav-links">
        <Link to={"/home"} className="nav-link">
          Home
        </Link>

        {islogedin() ? (
          <>
            <Link to={"/documents"} className="nav-link">
              Document
            </Link>
            <button onClick={downloadPdf} className="nav-link">
              Download PDF
            </button>
            <button onClick={logout} className="nav-link">
              Logout
            </button>
          </>
        ) : (
          <>
            <Link to={"/login"} className="nav-link">
              Login
            </Link>
            <Link to={"/signup"} className="nav-link">
              SignUp
            </Link>
          </>
        )}
      </div>
    </nav>
  );
}
