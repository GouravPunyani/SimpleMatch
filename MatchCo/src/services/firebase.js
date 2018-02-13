// @flow

import { initializeApp, auth } from 'firebase'

// TODO: move to .env file
const FIREBASE_CONFIG = {
  apiKey: 'AIzaSyDltX5yulteM200yf874GWc6Nv5Y7K_SxQ',
  authDomain: 'matchco2-8f65a.firebaseapp.com',
  databaseURL: 'https://matchco2-8f65a.firebaseio.com',
  projectId: 'matchco2-8f65a',
  storageBucket: 'matchco2-8f65a.appspot.com',
  messagingSenderId: '932170916330'
}

export type FirebaseSignInResponse = {
  uid: string,
  displayName?: string,
  photoURL?: string,
  email?: string,
};

export type FirebaseService = {
  signIn: (email: string, password: string) => Promise<FirebaseSignInResponse>,
  signOut: () => Promise<any>
};

export default function setupFirebase (): FirebaseService {
  initializeApp(FIREBASE_CONFIG)

  /**
   * Attempt to sign into firebase with a username and password.
   *
   * @param {string} email - The user's email address.
   * @param {string} password - The user's password.
   * @return {Promise<FirebaseAuthResult>} - The authentication result or an error.
   */
  async function signIn (
    email: string,
    password: string
  ): Promise<FirebaseSignInResponse> {
    const result = await auth().signInWithEmailAndPassword(email, password)
    return (result: FirebaseSignInResponse)
  }

  /**
   * Signout of firebase.
   */
  async function signOut (): Promise<any> {
    return auth().signOut()
  }

  return {
    signIn, signOut
  }
}
