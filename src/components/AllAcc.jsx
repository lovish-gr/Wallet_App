import axios from '../api/backendAPI'
import React, { useEffect, useState } from 'react'

function AllAcc() {
    const [data, setData] = useState([{}])
    useEffect(() => {
        axios.get(`/account/customer/${localStorage.getItem('custId')}`)
        .then((res) => {
            console.log(res)
            setData(res.data)
        })
    },[])

    return(
        <>

        
        <div>{data.length > 0 ? (
        <><ul>
          {data.map(account => (
            <li key={account.accountNumber}>
              Account Number:{account.accountNumber}, Type: {account.accType}, Balance: {account.openingBalance}, Opening Date: {account.openingDate}, Customer Id: {localStorage.getItem('custId')}
            </li>
          ))}
        </ul>
        </>
      ) : (
        <p>No accounts stored yet.</p>
      )}</div>
        </>
    )
}
export default AllAcc