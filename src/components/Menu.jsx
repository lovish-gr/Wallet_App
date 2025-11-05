import { Link, Links } from "react-router-dom";

export default function Menu() {
  return (
    <>
      <div className="flex flex-row m-4">
        <div className="basis-1/2 m-2 p-2 h-50 bg-blue-600/100 rounded-md">
            <p className="text-xl text-center font-bold text-white">Transactions</p>
            <div className="flex flex-row justify-between">
                <Link to={"/transfer"} className="basis-1/3 bg-blue-500/100 h-30 rounded-md m-2">
                  <div ><p className="text-center font-mono text-white font-bold">Transfer</p></div>
                </Link>
                <Link to={"/withdraw"} className="basis-1/3 bg-blue-500/100 h-30 rounded-md m-2">
                  <div ><p className="text-center font-mono text-white font-bold">Withdraw</p></div>
                </Link>
                <Link to={"/deposit"} className="basis-1/3 bg-blue-500/100 h-30 rounded-md m-2">
                  <div ><p className="text-center font-mono text-white font-bold">Deposit</p></div>
                </Link>
            </div>
        </div>
        <Link to={"/allAccounts"} className="basis-1/2 m-2 p-2 h-50 bg-blue-600/100 rounded-md">
        <div >
            <p className="text-xl text-center font-bold text-white">All Accounts</p>
        </div>
        </Link>
      </div>
    </>
  );
}
