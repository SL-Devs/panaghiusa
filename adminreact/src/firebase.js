
import { initializeApp } from "firebase/app";
import {getAuth} from 'firebase/auth'

const firebaseConfig = {
  apiKey: "AIzaSyBYNPlDhQ2bWlebMrMJ0yx3v6g_jVuMJ90",
  authDomain: "react-auth-8b930.firebaseapp.com",
  projectId: "react-auth-8b930",
  storageBucket: "react-auth-8b930.appspot.com",
  messagingSenderId: "872273124991",
  appId: "1:872273124991:web:4bc2d901b822067d79e94a",
  measurementId: "G-5S8N2XDTCZ"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export const auth = getAuth(app)
export default app;