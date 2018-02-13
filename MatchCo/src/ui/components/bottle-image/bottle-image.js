// @flow

import React, { Component } from 'react'
import { View, Image, StyleSheet } from 'react-native'
import { CurvedText } from '../..'
import { palette } from '../../theme'

export type Props = {
  face: 'front' | 'back',
  tint: ?string,
  name: ?string,
  date: ?string
};

export type State = {
  hasImageLoaded: boolean
};

const frontImage = require('./front.png')
const backImage = require('./back.png')

export class BottleImage extends Component<*, Props, State> {
  state = {
    hasImageLoaded: false
  };
  render () {
    const { face, tint = 'white' } = this.props
    const isBack = face === 'back'
    const isFront = !isBack
    const src = isBack ? backImage : frontImage
    const name = this.props.name
    const date = this.props.date
    const onLoadEnd = e => {
      this.setState({ hasImageLoaded: true })
    }
    const { hasImageLoaded } = this.state

    return (
      <View style={styles.wrapper}>
        {hasImageLoaded &&
          <View style={[styles.tint, { backgroundColor: tint }]} />}
        <Image
          style={styles.image}
          source={src}
          resizeMode='contain'
          onLoadEnd={onLoadEnd}
        />
        {isFront &&
          <View style={styles.frontName}>
            <CurvedText text={name} color={palette.deepBlack} fontSize={6} />
          </View>}
        {isBack &&
          <View style={styles.backName}>
            <CurvedText text={name} color={palette.deepBlack} fontSize={6} />
          </View>}
        {isBack &&
          <View style={styles.backDate}>
            <CurvedText text={date} color={palette.deepBlack} fontSize={6} />
          </View>}
      </View>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: { justifyContent: 'center', alignItems: 'center' },
  frontName: {
    position: 'absolute',
    alignItems: 'center',
    left: 0,
    right: 0,
    bottom: 76
  },
  backName: {
    position: 'absolute',
    alignItems: 'center',
    left: 0,
    right: 0,
    top: 133
  },
  backDate: {
    position: 'absolute',
    alignItems: 'center',
    left: 0,
    right: 0,
    top: 148
  },
  tint: { position: 'absolute', top: 100, width: 86, height: 199 },
  image: { height: 300 }
})
