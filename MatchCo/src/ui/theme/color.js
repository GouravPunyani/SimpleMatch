import chroma from 'chroma-js'

/**
 * The color palette. Try not to use these directly from outside this file.
 */
export const palette = {
  transparent: 'transparent',
  white: '#ffffff',
  black: '#000000',
  deepBlack: '#1d1d1d',
  nearBlack: '#333333',
  deepGray: '#464646',
  midGray: '#8C8C8C',
  lightGray: '#ddd',
  gold: '#E1CD96',
  pink: '#DCB8BD',
  darkerGold: '#D9C384',
  darkerPink: '#CFB0AB',
  facebook: '#3b5998',
  red: '#FF4136'
}

/**
 * The color roles. Try to use these where possible.
 */
export const color = {
  /** The background color used on screens. */
  background: palette.white,

  /** The black color used for default text. */
  text: palette.nearBlack,

  /** a lighter text color */
  textLighter: palette.midGray,

  /** The predominant brand color */
  brand: palette.pink,

  /** The drawer's background color */
  drawerBackground: palette.nearBlack,

  /** The drawer text */
  drawerText: palette.white,

  /** lines */
  line: palette.nearBlack,

  /** line, but lighter */
  lineLight: palette.lightGray,

  /** button background */
  buttonBackground: palette.pink,

  /** when buttons are disabled */
  buttonBackgroundDisabled: chroma(palette.darkerPink).mix('white', 0.7),

  /** button text */
  buttonText: palette.white,

  /** button text */
  buttonTextDisabled: chroma(palette.darkerPink).mix('white', 0.3),

  /** the error color */
  error: palette.red
}
