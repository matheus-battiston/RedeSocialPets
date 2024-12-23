import './App.css';
import {RouterProvider} from "react-router-dom";
import {router} from "./router";
import {GlobalUserProvider} from "./context/user.context";
import {GlobalErroProvider} from "./context/erro/erro.context";

function App() {
  return (
      <GlobalErroProvider>
          <GlobalUserProvider>
              <RouterProvider router={router} />
          </GlobalUserProvider>
      </GlobalErroProvider>

  );
}

export default App;
