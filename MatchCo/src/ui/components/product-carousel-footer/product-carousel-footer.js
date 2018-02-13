// @flow

import React, { PureComponent } from 'react'
import { View, StyleSheet } from 'react-native'
import { Text } from '../..'

export type Props = {
  children?: React.Children
}

export class ProductCarouselFooter extends PureComponent<*, Props, *> {
  render () {
    return (
      <View style={styles.wrapper}>
        <Text style={styles.text}>product-carousel-footer.js</Text>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    flex: 1,
    backgroundColor: 'steelblue',
    justifyContent: 'center',
    alignItems: 'center'
  },
  text: {
    fontSize: 30
  }
})
