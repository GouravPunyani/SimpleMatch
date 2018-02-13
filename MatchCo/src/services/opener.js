// @flow

import { Linking } from 'react-native'

/**
 * Attemps to open a url/phone/email via Linking.
 */
async function open (url: string): Promise<boolean> {
  const ok = await Linking.canOpenURL(url)
  if (!ok) return false
  const opened = await Linking.openURL(url)
  return opened
}

/** Calls someone on the phone. */
export async function callPhone (phone: string): Promise<boolean> {
  return open(`tel://${phone.replace(/\./, '')}`)
}

/** Sends an email. */
export async function sendEmail (email: string): Promise<boolean> {
  return open(`mailto://${email}`)
}

/** Opens a browser. */
export async function visitUrl (url: string): Promise<boolean> {
  return open(url)
}
