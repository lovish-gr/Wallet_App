import React, { useEffect } from 'react'
import Navbar from './Navbar.jsx'
import UploadingCustomers from '../components/UploadingCustomers.jsx'

function UploadCustomersPage() {
    function islogedin(){
        const token = localStorage.getItem("authId");
        if(token!=null){
          return true;
        }else{
          return false;
        }
      }
    useEffect(() => {
        if(islogedin()){
            console.log("Admin is logged in");
        }else{
            window.location.href = "/auth";
        }
    }, []);

    return (
        <>
         <Navbar></Navbar>
         <UploadingCustomers/>
        </>
    )
}
export default UploadCustomersPage