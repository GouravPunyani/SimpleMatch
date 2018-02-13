/**
 * A sample is a single color calibration line.
 */
export type Sample = {
  'sample_Red': number,
  'sample_Green': number,
  'sample_Blue': number,
  'sample_L': number,
  'sample_A': number,
  'sample_B': number,
  sampleLum?: number,
  specLum?: number
}
