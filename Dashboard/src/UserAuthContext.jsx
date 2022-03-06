import { createContext, useContext, useEffect, useState } from "react";

import {
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword,
  onAuthStateChanged,
  signOut,
  GoogleAuthProvider,
  signInWithPopup,
} from "firebase/auth";
import { db, auth } from "./Firebase";
import { confirm } from "react-confirm-box";
import { set, ref, onValue, remove, update } from "firebase/database";

const userAuthContext = createContext();
export function UserAuthContextProvider({ children }) {
  const [user, setUser] = useState({});
  const [data, setData] = useState([]);
  const [RealtimeData, setRealtimeData] = useState([]);
  const [OrganicContribution, setOrganicContribution] = useState([]);

  //Read the Data
  useEffect(() => {
    onValue(ref(db, "Users/"), (snapshot) => {
      setData([]);
      const data = snapshot.val();
      if (data !== null) {
        Object.values(data).map((todo) => {
          setData((oldArray) => [...oldArray, todo]);
        });
      }
    });
  }, []);

  useEffect(() => {
    onValue(ref(db, "PlasticContribution/"), (snapshot) => {
      setRealtimeData([]);
      const data = snapshot.val();
      if (data !== null) {
        Object.values(data).map((todo) => {
          setRealtimeData((oldArray) => [...oldArray, todo]);
        });
      }
    });
  }, []);

  useEffect(() => {
    onValue(ref(db, "OrganicContribution/"), (snapshot) => {
      setOrganicContribution([]);
      const data = snapshot.val();
      if (data !== null) {
        Object.values(data).map((todo) => {
          setOrganicContribution((oldArray) => [...oldArray, todo]);
        });
      }
    });
  }, []);

  const options = {
    labels: {
      confirmable: "Confirm",
      cancellable: "Cancel",
    },
  };

  function logIn(email, password) {
    return signInWithEmailAndPassword(auth, email, password);
  }
  function signUp(email, password) {
    return createUserWithEmailAndPassword(auth, email, password);
  }
  function logOut() {
    return signOut(auth);
  }
  function googleSignIn() {
    const googleAuthProvider = new GoogleAuthProvider();
    return signInWithPopup(auth, googleAuthProvider);
  }

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, (currentuser) => {
      console.log("Auth", currentuser);
      setUser(currentuser);
    });

    return () => {
      unsubscribe();
    };
  }, []);

  return (
    <userAuthContext.Provider
      value={{
        user,
        logIn,
        signUp,
        logOut,
        googleSignIn,
        data,

        setData,
        RealtimeData,
        setRealtimeData,
        OrganicContribution,
        setOrganicContribution,
      }}
    >
      {children}
    </userAuthContext.Provider>
  );
}

export function useUserAuth() {
  return useContext(userAuthContext);
}
