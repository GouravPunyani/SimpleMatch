// @flow
import type { Sample } from '../entities/sample'
import type { Scan } from '../entities/scan'
import { NativeModules, Platform } from 'react-native'
import RNFetchBlob from 'react-native-fetch-blob'
import {
  trim,
  last,
  split,
  pipe,
  head,
  slice,
  zipObj,
  map,
  replace
} from 'ramda'


/**
 * The directory where the native code places its scan files.
 */
export function getScanDirectory (): string {
  return `${RNFetchBlob.fs.dirs.SDCardDir}/Scans`
}

/**
 * Begins the new scan process and returns the scan if found.
 */
export async function scan () {
  // disable iOS support for now
  if (Platform.OS === 'ios') {
    return null
  }
  const result = await NativeModules.Scan.launch()
  if (result.success) {
    const filename = `${getScanDirectory()}/${result.filename}`
    return parseScanFile(filename)
  }
  return null
}

/**
 * Gets the last scan data.
 *
 * @return The last scan data or null.
 */
export async function getLatestScan (): Promise<?Scan> {
  const { fs } = RNFetchBlob
  const dir = getScanDirectory()

  // disable iOS support for now
  if (Platform.OS === 'ios') {
    return null
  }

  // We can't `ls` unless we have permissions, so check first.  Permissions
  // are lazily asked for (a good thing), so first installs of this app, won't
  // have permission just yet.
  const perm = await NativeModules.Scan.checkReadExternalStoragePermission()
  if (perm !== 'granted') {
    return null
  }

  const ls = await fs.ls(dir)
  if (ls.length === 0) return null
  // the dirs are sorted chronologically, so we pull the last one
  const scanfile = `${dir}/${last(ls)}`
  return parseScanFile(scanfile)
}

/**
 * Extracts the device used for the scan.
 *
 * @param lines The lines of the scan file.
 */
export const getDevice: (lines: Array<string>) => string = pipe(
  last,
  split(/\s/),
  head
)

/**
 * Extracts the protocol version of the scan file.
 *
 * @param lines The lines of the scan file.
 * @return The protocol version.
 */
export const getVersion: (lines: Array<string>) => string = pipe(
  last,
  split(/\s/),
  last
)

/**
 * Extracts the date of the scan file.
 *
 * @param path The full path of the scan file.
 * @return The date of the capture.
 */
export const getDate: (path: string) => Date = pipe(
  split('/'),
  last,
  replace('.txt', ''),
  replace('ScanOutput_', ''),
  split('_'),
  numbers => new Date(...numbers)
)

/**
 * Converts raw Sample data into a structure.
 */
export const convertColorLine: (lines: Array<string>) => Sample = zipObj([
  'sample_Red',
  'sample_Green',
  'sample_Blue',
  'sample_L',
  'sample_A',
  'sample_B',
  'sampleLum',
  'specLum'
])

/**
 * Parses the Sample lines of a scan file into structured data.
 *
 * @param lines The lines of the scan file.
 * @return An array of Sample objects.
 */
export const getSamples: (lines: Array<string>) => Array<Sample> = pipe(
  slice(1, -1), // skip the first & last lines
  map(pipe(split('\t'), convertColorLine))
)

/**
 * Parses the scan file into a data structure.
 *
 * @param {string} filename The full path to the scan file.
 */
export async function parseScanFile (filename: string): Promise<Scan> {
  const { fs } = RNFetchBlob
  const rawFile = await fs.readFile(filename, 'utf8')
  const lines = split('\r', trim(rawFile))
  const data = ({
    filename,
    date: getDate(filename),
    device: getDevice(lines),
    version: getVersion(lines),
    samples: getSamples(lines),
    raw: rawFile
  }: Scan)
  return data
}
