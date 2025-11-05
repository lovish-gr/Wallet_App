import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Cookies from 'js-cookie';
import axios from "../api/backendAPI"
function AccForm() {
  const [values, setValues] = useState({
    customerId: localStorage.getItem('custId'),
    accType: "",
    openingBalance: 0,
    description: ""
  }); 
  const navigate = useNavigate();
  const handelSub = (event) => {
    event.preventDefault();
    console.log(values);
    axios.post("/account/create", values).then((res) => {
    //   console.log(res);
      if (res.data.message != null) {
        console.log(res.data);
        alert(res.data.message );
        navigate("/")
      } else {
        alert("error");
      }
    });
  };
  return (
    <>
      <div className="flex items-center justify-center p-12">
        <div className="mx-auto w-full max-w-[550px] bg-white">
          <form onSubmit={handelSub}>
            
            <div className="mb-5">
              <label
                htmlFor="lastName"
                className="mb-3 block text-base font-medium text-[#07074D]"
              >
                Customer Id
              </label>
              <input
                type="number"
                name="customerId"
                id="customerId"
                placeholder="Customer Id"
                defaultValue={localStorage.getItem('custId')}
                disabled
                className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
              />
            </div>
            <div className="mb-5">
              <label
                htmlFor="AccType"
                className="mb-3 block text-base font-medium text-[#07074D]"
              >
                AccType
              </label>
              <input
                type="text"
                name="AccType"
                id="AccType"
                placeholder="Enter your account type"
                onChange={(e) =>
                  setValues({ ...values, accType: e.target.value })
                }
                className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
              />
            </div>
            <div className="mb-5">
              <label
                htmlFor="openingBalance"
                className="mb-3 block text-base font-medium text-[#07074D]"
              >
                Opening Balance
              </label>
              <input
                type="number"
                name="opening balance"
                id="opening balance"
                placeholder="Enter your opening balance"
                onChange={(e) =>
                  setValues({ ...values, openingBalance: e.target.value })
                }
                className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
              />
            </div>
            <div className="mb-5">
              <label
                htmlFor="description"
                className="mb-3 block text-base font-medium text-[#07074D]"
              >
                Description
              </label>
              <input
                type="text"
                name="description"
                id="description"
                placeholder="Enter your desc"
                onChange={(e) =>
                  setValues({ ...values, description: e.target.value })
                }
                className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
              />
            </div>
            <div>
              <button
                type="submit"
                className="hover:shadow-form w-full rounded-md bg-[#6A64F1] py-3 px-8 text-center text-base font-semibold text-white outline-none"
              >
                Create Account
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}
export default AccForm;
