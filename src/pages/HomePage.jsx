import React from 'react'
import Navbar from '../components/Navbar'
import Footer from '../components/Footer'
import Hero from '../components/Hero'
import Menu from '../components/Menu'
import { useLocation, useNavigate } from 'react-router-dom'

function HomePage() {
    // const 
    const islogedin = () => {
      if (localStorage.getItem('custId') != null) {
            return true;
      }else{
        return false;
      }
    };

    const navigate = useNavigate();


    const location = useLocation();
    return(
        <>
            <Navbar/>
            {
                islogedin() ? (
                    <>
                <Hero data={location.state}/>
                <Menu/>
                </>
            ) 
                : (navigate('/login'))
            }
            <Footer/>
        </>
    )
}
export default HomePage