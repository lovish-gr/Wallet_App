import HomePage from "./pages/HomePage.jsx"
import "./index.css"
import { Route, Routes } from "react-router-dom"
import LogIn from "./pages/Login.jsx"
import SignUp from "./pages/SignUp.jsx"
import CreateAcc from "./pages/CreateAcc.jsx"
import ShowAllAccounts from "./pages/ShowAllAccounts.jsx"
import TransferPage from "./pages/TransferPage.jsx"
import DepositPage from "./pages/DepositPage.jsx"
import WithdrawPage from "./pages/WithdrawPage.jsx"
import UploadDocumentPage from "./pages/UploadDocumentPage.jsx"
import AadhaarVerifier from "./pages/AadharVerifier.jsx"
import AdminPanel from "./admin/AdminPanel.jsx"
import Auth from "./admin/Auth.jsx"
import UploadCustomersPage from "./admin/UploadCustomersPage.jsx"
import AllCustomerPage from "./admin/AllCustomerPage.jsx"

function App() {

  return (
    <>
    <Routes>
      <Route path='/home' element={ <HomePage/> }></Route>
      <Route path='/*' element={ <LogIn/> }></Route>
      <Route path='/signup' element={ <SignUp/> }></Route>
      <Route path='/auth' element={ <Auth/> }></Route>
      <Route path='/admin-panel' element={ <AdminPanel/> }> </Route>
      <Route path='/uploadCustomers' element={ <UploadCustomersPage/> }></Route>
      <Route path='/allCustomers' element={ <AllCustomerPage/> }></Route>
      <Route path='/documents' element={ <UploadDocumentPage/> }></Route>
      <Route path="/createAcc" element={<CreateAcc></CreateAcc>}></Route>
      <Route path="/allAccounts" element={<ShowAllAccounts/>}></Route>
      <Route path="/transfer" element={<TransferPage></TransferPage>}></Route>
      <Route path="/deposit" element={<DepositPage/>}></Route>
      <Route path="/withdraw" element={<WithdrawPage/>}></Route>
      <Route path="/test" element={<AadhaarVerifier/>}></Route>
    </Routes>
    </>
  )
}

export default App
