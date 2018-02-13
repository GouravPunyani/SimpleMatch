// @flow

import React, { Component } from 'react'
import { StyleSheet, Text as ReactNativeText, View } from 'react-native'
import Svg, { Text, Path, G, Defs, TextPath } from 'react-native-svg'
import { fontFamily } from '../../theme'

export type Props = {
  text: string,
  color: string,
  fontSize: number
};

export type State = {
  width: number
};

const width = 86
const height = 20
const arcTo = height * 0.6
const xOffset = 0
const path = `
  M ${xOffset},${height * 0.5}
  C ${xOffset},${height * 0.5} ${width * 0.5 + xOffset},${arcTo} ${width + xOffset},${height * 0.5}
`

export class CurvedText extends Component<*, Props, State> {
  state = {
    width: 0
  };

  render () {
    const { fontSize = 6 } = this.props
    const { width: measuredWidth } = this.state
    const hasMeasured = measuredWidth > 0
    const onLayout = x => {
      if (!hasMeasured) {
        const newWidth = x.nativeEvent.layout.width
        this.setState({ width: newWidth })
      }
    }

    const pctOffset = hasMeasured
      ? (width - measuredWidth) * 0.5 / width * 100.0
      : 0

    const startOffset = `${Math.round(pctOffset)}%`

    const rulerStyle = {
      position: 'absolute',
      color: 'rgba(255, 255, 255, 0.01)',
      backgroundColor: 'transparent',
      fontFamily: fontFamily.nexa,
      fontSize: fontSize
    }

    return (
      <View style={styles.wrapper}>
        <ReactNativeText style={rulerStyle} onLayout={onLayout}>
          {this.props.text}
        </ReactNativeText>
        <Svg
          key={`svg-${startOffset}`}
          style={styles.wrapper}
          height={height}
          width={width}
        >
          <Defs>
            <Path id='path' d={path} />
          </Defs>
          <G>
            <Text
              textAnchor='start'
              fontSize={fontSize}
              fontFamily={fontFamily.nexa}
              fill={this.props.color}
            >
              <TextPath href='#path' startOffset={startOffset}>
                {this.props.text}
              </TextPath>
            </Text>

          </G>
        </Svg>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    backgroundColor: 'transparent'
  }
})
