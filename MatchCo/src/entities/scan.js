import type { Sample } from './sample'

/**
 * A scan is when the user performs an end-to-end scan of their face.
 */
export type Scan = {
  /** The full path to the saved scan file. */
  filename: string,
  /** The date of the scan in UTC. */
  date: Date,
  /** The device model used */
  device: string,
  /** The protocol version of the scan file. */
  version: string,
  /** The parsed sample data. */
  samples: Array<Sample>,
  /** The unparsed contents of the scan file. */
  raw: string
}
