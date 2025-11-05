import axios from "../api/backendAPI";
import React, { useEffect, useState } from "react";
import { Download } from "lucide-react";
import TablePagination from "@mui/material/TablePagination";
function AllCustomers({ searchQuery }) {
  const [values, setValues] = useState({
    page: 0,
    size: 10,
    sort: "firstName,asc",
  });
  const [customerData, setCustomerData] = useState([]);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const downloadPdf = async (customerId) => {
    if (!customerId) {
      alert("Customer ID not found!");
      return;
    }

    try {
      const response = await axios.get(`/customer/pdf/${customerId}`, {
        responseType: "blob",
        headers: {
          Accept: "application/pdf",
        },
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

  useEffect(() => {
    fetchCustomers();
  }, [values, searchQuery]);

  const fetchCustomers = async () => {
    setLoading(true);
    try {
      const res = await axios.post("/admin/list", {
        ...values,
        search: searchQuery || "",
      });
      setCustomerData(res.data.content || []);
      setTotalPages(res.data.totalPages || 1);
    } catch (err) {
      console.error("Error fetching customer data:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleSort = (type) => {
    const [currentField, currentOrder] = values.sort.split(",");
    const newOrder =
      currentField === type && currentOrder === "asc" ? "desc" : "asc";
    setValues({ ...values, sort: `${type},${newOrder}` });
  };

  //   const handlePageChange = (newPage) => {
  //     if (newPage >= 0 && newPage < totalPages) {
  //       setValues({ ...values, page: newPage });
  //     }
  //   };
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
    setValues({ ...values, page: newPage });
  };
  const handleChangeRowsPerPage = (event) => {
    const newSize = parseInt(event.target.value, 10);
    setRowsPerPage(newSize);
    setValues({ ...values, size: newSize, page: 0 });
    setPage(0);
  };

  return (
    <div className="m-4">
      <div className="overflow-x-auto shadow-xl rounded-2xl border border-gray-200">
        <table className="w-full text-sm text-left text-gray-600">
          <thead className="text-xs text-gray-700 uppercase bg-gray-100">
            <tr>
              {["firstName", "lastName", "emailId"].map((col) => (
                <th
                  key={col}
                  scope="col"
                  className="px-6 py-3 cursor-pointer select-none"
                  onClick={() => handleSort(col)}
                >
                  <div className="flex items-center justify-between">
                    {col === "firstName"
                      ? "First Name"
                      : col === "lastName"
                      ? "Last Name"
                      : "Email Id"}
                    {values.sort.startsWith(col) && (
                      <span className="ml-1 text-xs">
                        {values.sort.endsWith("asc") ? "↑" : "↓"}
                      </span>
                    )}
                  </div>
                </th>
              ))}
              <th scope="col" className="px-6 py-3">
                Contact No.
              </th>
              <th scope="col" className="px-6 py-3 text-center">
                Action
              </th>
            </tr>
          </thead>

          <tbody>
            {loading ? (
              <tr>
                <td colSpan="5" className="text-center py-6 text-gray-500">
                  Loading customers...
                </td>
              </tr>
            ) : customerData.length > 0 ? (
              customerData.map((customer) => (
                <tr
                  key={customer.customerId}
                  className="bg-white border-b hover:bg-gray-50 transition"
                >
                  <td className="px-6 py-4 font-medium text-gray-900">
                    {customer.firstName || "-"}
                  </td>
                  <td className="px-6 py-4">{customer.lastName || "-"}</td>
                  <td className="px-6 py-4">{customer.emailId || "-"}</td>
                  <td className="px-6 py-4">{customer.contactNo || "-"}</td>
                  <td className="px-6 py-4 text-center">
                    <button
                      onClick={() => downloadPdf(customer.customerId)}
                      className="flex items-center gap-1 bg-blue-600 hover:bg-blue-700 text-white px-3 py-1.5 rounded-lg transition"
                    >
                      <Download size={16} /> PDF
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="5" className="text-center py-6 text-gray-500">
                  No customers found.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      <TablePagination
        component="div"
        count={customerData.length * totalPages}
        page={page}
        onPageChange={handleChangePage}
        rowsPerPage={rowsPerPage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </div>
  );
}

export default AllCustomers;
