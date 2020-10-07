import React from 'react';
import {useLocation} from "react-router-dom";
import './App.css';
import BoAppSkeleton from './bo/BoAppSkeleton'
import CinemaAppSkeleton from "./cinema/CinemaAppSkeleton";

function App () {
    const location = useLocation()
    return (
        <div className="App">
            { location.hash.includes("#/bo") ?
                <BoAppSkeleton/> :
                <CinemaAppSkeleton/>
            }
        </div>
    );
}

export default App;
