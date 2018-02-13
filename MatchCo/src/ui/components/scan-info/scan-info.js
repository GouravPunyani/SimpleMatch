// @flow

import type { Scan } from '../../../entities/scan'
import React, { Component } from 'react'
import { View, StyleSheet } from 'react-native'
import { Text } from '../..'
import { translator } from '../../../services/translate/translator'
import { spacing } from '../../theme'
import type { Sample } from '../../../entities/sample'
import { bind } from 'decko'

export type Props = {
  scan?: Scan,
  translate: (key: string) => string
}

@translator
export class ScanInfo extends Component<*, Props, *> {
  renderNoScan () {
    const { translate } = this.props
    const text = translate('scanScreen.noScan')
    return (
      <Text title text={text} />
    )
  }

  @bind
  renderSample (sample: Sample, i: number) {
    const r = sample['sample_Red'] * 255
    const g = sample['sample_Green'] * 255
    const b = sample['sample_Blue'] * 255
    // console.tron.log({ sample })
    const sampleStyle = {
      backgroundColor: `rgba(${r},${g},${b},1)`
    }
    return (
      <View key={`${i}`} style={[styles.sample, sampleStyle]} />
    )
  }

  render () {
    const { scan } = this.props
    if (!scan) {
      return this.renderNoScan()
    }
    return (
      <View style={styles.wrapper}>
        <View style={styles.row}>
          <Text title brand text='Date' />
          <Text text={scan.date.toString()} />
        </View>
        <View style={styles.row}>
          <Text title brand text='Device' />
          <Text text={scan.device} />
        </View>
        <View style={styles.row}>
          <Text title brand text='File Version' />
          <Text text={scan.version} />
        </View>
        <View style={styles.row}>
          <Text title brand text='Samples' />
          <View style={styles.samples}>
            {
              scan.samples.map(this.renderSample)
            }
          </View>
        </View>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
  },
  row: {
    paddingVertical: spacing.small
  },
  samples: {
    flexWrap: 'wrap',
    flexDirection: 'row'
  },
  sample: {
    width: spacing.large,
    height: spacing.large
  }
})
