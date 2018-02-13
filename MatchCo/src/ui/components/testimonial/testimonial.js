// @flow

import React, { PureComponent } from 'react'
import { View, StyleSheet, Image, TouchableOpacity } from 'react-native'
import { Text } from '../..'
import { Testimonial as TestimonialEntity } from '../../../entities/testimonial'
import { color, spacing } from '../../theme'
import { translator } from '../../../services/translate/translator'

export type Props = {
  testimonial: TestimonialEntity,
  translate: (key: string) => string,
  reverse: boolean,
  onPress: () => void
}

@translator
export class Testimonial extends PureComponent<*, Props, *> {
  render () {
    const { testimonial, translate } = this.props
    const style = [styles.wrapper]
    if (this.props.reverse) {
      style.push(styles.reverse)
    }
    return (
      <View style={style}>
        <TouchableOpacity style={styles.person} onPress={this.props.onPress}>
          <View style={styles.imageWrapper}>
            <Image source={testimonial.image} resizeMode='contain' />
          </View>
          <Text small brand text={testimonial.name} />
          <Text white style={styles.watchText} text={translate('product.watchTestimonial')} />
        </TouchableOpacity>
        <View style={styles.quote}>
          <Text large style={styles.quoteText}>"{testimonial.quote}"</Text>
        </View>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingVertical: spacing.medium,
    paddingHorizontal: spacing.small,
    borderBottomWidth: 1,
    borderBottomColor: color.brand
  },
  reverse: {
    flexDirection: 'row-reverse'
  },
  person: {
    alignItems: 'center',
    width: 90
  },
  quote: {
    flex: 1,
    padding: spacing.medium
  },
  imageWrapper: {
  },
  quoteText: {
    textAlign: 'center',
    color: color.textLighter
  },
  watchText: {
    padding: spacing.extraExtraSmall,
    textAlign: 'center',
    backgroundColor: color.buttonBackground,
    color: color.buttonText,
    fontSize: 8
  }
})
