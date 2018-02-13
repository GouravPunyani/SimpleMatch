// import { PropTypes } from 'react'
import { requireNativeComponent, View } from 'react-native'

const scanViewInterface = {
  name: 'ScanView',
  propTypes: {
    ...View.propTypes
  }
}

export const ScanView = requireNativeComponent('ScanView', scanViewInterface)
