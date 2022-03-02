
import {initializeApp} from "firebase/app";
import { getDatabase } from "firebase/database";
import { getAuth ,} from "firebase/auth";
import {getStorage} from 'firebase/storage'


  const firebaseConfig = {
    apiKey: "AIzaSyAGtGB6JizIxR5mNCjgXctLJc9DuwzvFY4",
    authDomain: "panaghiusa-28480.firebaseapp.com",
    databaseURL: "https://panaghiusa-28480-default-rtdb.firebaseio.com",
    projectId: "panaghiusa-28480",
    storageBucket: "panaghiusa-28480.appspot.com",
    messagingSenderId: "178419737414",
    appId: "1:178419737414:web:137be01e43cc415ab8bb47",
    measurementId: "G-R0V427VN0S"
  
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export  const db = getDatabase(app);
export const auth = getAuth(app)
export const storage = getStorage(app)
