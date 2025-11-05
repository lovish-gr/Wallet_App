import axios from "../api/backendAPI";
import React, { useState } from "react";
function uploadingCustomers() {
  const [successEntries, setSuccessEntries] = useState([]);
  const [errorEntries, setErrorEntries] = useState([
    {
      rowNumber: null,
      errorMessage: "",
    },
  ]);
  const [fileName, setFileName] = useState("");

  const handleFileUpload = async (e) => {
    const file = e.target.files[0];
    setFileName(file ? file.name : "");
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);
    axios
      .post("/admin/upload", formData)
      .then((res) => {
        console.log(res.data.successEntries);
        const { successEntries, errorEntries } = res.data;
        setSuccessEntries(successEntries || []);
        setErrorEntries(errorEntries || []);
      })
      .catch((err) => {
        console.error("Upload failed:", err);
        alert("File upload failed. Please try again.");
      });
  };
  return (
    <div
      style={{
        maxWidth: 700,
        margin: "40px auto",
        padding: 24,
        background: "#fff",
        borderRadius: 8,
      }}
    >
      <h2>Admin Panel</h2>
      <div style={{ marginBottom: 24 }}>
        <label htmlFor="excel-upload" style={{ fontWeight: 500 }}>
          Bulk Upload (Excel):
        </label>
        <input
          id="excel-upload"
          type="file"
          accept=".xlsx,.xls"
          onChange={handleFileUpload}
          style={{ display: "block", marginTop: 8 }}
        />
        {fileName && (
          <div style={{ marginTop: 8, color: "#888" }}>
            Selected: {fileName}
          </div>
        )}
      </div>

      <div style={{ display: "flex", gap: 32 }}>
        <div style={{ flex: 1 }}>
          <h3 style={{ color: "green" }}>Successful Entries</h3>
          {successEntries.length === 0 ? (
            <div>No successful entries yet.</div>
          ) : (
            <table
              border="1"
              cellPadding="8"
              style={{ width: "100%", borderCollapse: "collapse" }}
            >
              <thead>
                <tr>
                  <th>Email</th>
                </tr>
              </thead>
              <tbody>
                {successEntries.map((entry) => (
                  <tr>
                    <td>{entry}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
        <div style={{ flex: 1 }}>
          <h3 style={{ color: "red" }}>Error Entries</h3>
          {errorEntries.length === 0 ? (
            <div>No error entries yet.</div>
          ) : (
            <table
              border="1"
              cellPadding="8"
              style={{ width: "100%", borderCollapse: "collapse" }}
            >
              <thead>
                <tr>
                  <th>Row</th>
                  <th>Error</th>
                </tr>
              </thead>
              <tbody>
                {errorEntries.map((entry, idx) => (
                  <tr key={idx}>
                    <td>{entry.rowNumber}</td>
                    <td>{entry.errorMessage}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </div>
    </div>
  );
}

export default uploadingCustomers;
