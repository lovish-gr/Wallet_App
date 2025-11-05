import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./Navbar.css";
import axios from "../api/backendAPI";

export default function Navbar({ onSearch }) { // 👈 accept callback from parent
  const [query, setQuery] = useState("");

  const islogedin = () => !!localStorage.getItem("authId");

  const logout = () => {
    localStorage.removeItem("authId");
    window.location.reload();
  };

  // Debounce search (400ms delay)
  useEffect(() => {
    const timeout = setTimeout(() => {
      if (onSearch) onSearch(query);
    }, 400);
    return () => clearTimeout(timeout);
  }, [query, onSearch]);

  const downloadPdf = async () => {
    const customerId = localStorage.getItem("custId");
    if (!customerId) return alert("Customer ID not found!");

    try {
      const response = await axios.get(`/customer/pdf/${customerId}`, {
        responseType: "blob",
        headers: { Accept: "application/pdf" },
      });

      const blob = new Blob([response.data], { type: "application/pdf" });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", `customer_${customerId}.pdf`);
      document.body.appendChild(link);
      link.click();
      link.remove();
      window.URL.revokeObjectURL(url);
    } catch (error) {
      console.error("Error downloading PDF:", error);
      alert("Failed to download PDF. Please try again.");
    }
  };

  return (
    <nav className="navbar">
      <Link to={"/admin-panel"} className="logo">
        Digi Wallet AP
      </Link>

      <div className="relative hidden md:block">
        <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
          <svg
            className="w-4 h-4"
            aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 20 20"
          >
            <path
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"
            />
          </svg>
        </div>
        <input
          type="text"
          id="search-navbar"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Search by name or email..."
          className="block w-full p-2 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 
                     focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 
                     dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        />
      </div>

      <div className="nav-links">
        {islogedin() ? (
          <>
            <button onClick={logout} className="nav-link">
              Logout
            </button>
          </>
        ) : (
          <Link to={"/auth"} className="nav-link">
            Login
          </Link>
        )}
      </div>
    </nav>
  );
}
