package co.getmatch.ScanApp;
import android.app.Activity;
import android.os.Bundle;

/*

  Copyright (C) 2000-2017 Young Harvill

  This software is provided 'as-is', without any express or implied
  warranty.  In no event will the author be held liable for any damages
  arising from the use of this software.

  Permission is granted to anyone to use this software for any purpose,
  including commercial applications, and to alter it and redistribute it
  freely, subject to the following restrictions:

  1. The origin of this software must not be misrepresented; you must not
     claim that you wrote the original software. If you use this software
     in a product, an acknowledgment in the product documentation would be
     appreciated but is not required.
  2. Altered source versions must be plainly marked as such, and must not be
     misrepresented as being the original software.
  3. This notice may not be removed or altered from any source distribution.

*/

public class CvColor {
    /**
     * Converts from linear RGB to RGB
     * @param rgbColor, The output RGB color
     * @param linRGBColor, The input linear RGB color
     */
    public static void linRGBToRGB(float rgbColor[],  float linRGBColor[])
    {
        double R = linRGBColor[0];
        double G = linRGBColor[1];
        double B = linRGBColor[2];

        // apply gamma of 2.4 to linear sRGB
        if (R > 0.0031308f)
        {
            R = (1.055 *  java.lang.Math.pow(R, 1.0/2.4)) - 0.055f;
        }
        else
        {
            R = 12.92 * R;
        }
        if (G > 0.0031308)
        {
            G = (1.055 *  java.lang.Math.pow(G, 1.0/2.4)) - 0.055f;
        }
        else
        {
            G = 12.92 * G;
        }
        if (B > 0.0031308)
        {
            B = (1.055 *  java.lang.Math.pow(B, 1.0/2.4)) - 0.055f;;
        }
        else
        {
            B = 12.92 * B;
        }

        rgbColor[0] = (float)R;
        rgbColor[1] = (float)G;
        rgbColor[2] = (float)B;
    }

    /**
     * Converts from RGB to linear RGB
     * @param linRGBColor, The output linear RGB color
     * @param rgbColor, The input RGB color
     */
    public static void rgbTolinRGB(float linRGBColor[],  float rgbColor[])
    {
        double R = rgbColor[0];
        double G = rgbColor[1];
        double B = rgbColor[2];

        // convert to linear sRGB

        if (R > 0.04095)
        {
            R = java.lang.Math.pow((R + 0.055) * (1/1.055), 2.4);
        }
        else
        {
            R = R * (1/12.92);
        }

        if (G > 0.04095)
        {
            G = java.lang.Math.pow((G + 0.055) * (1/1.055), 2.4);
        }
        else
        {
            G = G * (1/12.92);
        }

        if (B > 0.04095)
        {
            B = java.lang.Math.pow((B + 0.055) * (1/1.055), 2.4);
        }
        else
        {
            B = B * (1/12.92);
        }
        linRGBColor[0] = (float)R;
        linRGBColor[1] = (float)G;
        linRGBColor[2] = (float)B;
    }

    /**
     * Converts from linear RGB to XYZ
     * @param xyzColor, The output XYZ color
     * @param linRGBColor, The input linear RGB color
     */
    public static void linRGBToXYZ(float xyzColor[],  float linRGBColor[])
    {
        float R = linRGBColor[0];
        float G = linRGBColor[1];
        float B = linRGBColor[2];

        // convert linear sRGB to XYZ
        float X = (0.4124f * R) + (0.3576f * G) + (0.1805f * B);
        float Y = (0.2126f * R) + (0.7152f * G) + (0.0722f * B);
        float Z = (0.0193f * R) + (0.1192f * G) + (0.9505f * B);

        xyzColor[0] = X;
        xyzColor[1] = Y;
        xyzColor[2] = Z;
    }

    /**
     * Converts from XYZ to linear RGB
     * @param linRGBColor, The output linear RGB color
     * @param xyzColor, The input XYZ color
     */
    public static void xyzTolinRGB(float linRGBColor[],  float xyzColor[])
    {
        float X = xyzColor[0];
        float Y = xyzColor[1];
        float Z = xyzColor[2];

        // convert from XYZ to linear sRGB
        float R = 3.240479f * X - 1.53715f  * Y  - 0.498535f * Z;
        float G = -0.969256f * X + 1.875991f * Y  + 0.041556f * Z;
        float B = 0.055648f * X - 0.204043f * Y  + 1.057311f * Z;

        linRGBColor[0] = R;
        linRGBColor[1] = G;
        linRGBColor[2] = B;
    }

    /**
     * Converts from LAB to XYZ
     * @param xyzColor, The output XYZ color
     * @param labColor, The input LAB color
     */
    public static void labToXYZ(float xyzColor[],  float labColor[])
    {
        // white point for XYZ conversion
        float Yn = 1.0f;
        float Xn = 0.950455f;
        float Zn = 1.088753f;

        // convert LAB to XYZ space using whitepoint.
        float P = (labColor[0] + 16) / 116;
        float X = ((labColor[1] / 500.0f) + P);
        X = X*X*X;
        X *= Xn;

        float Y = P*P*P;
        Y *= Yn;

        float Z = P-(labColor[2] / 200.0f);
        Z = Z*Z*Z;
        Z *= Zn;

        xyzColor[0] = X;
        xyzColor[1] = Y;
        xyzColor[2] = Z;
    }

    /**
     * Converts from XYZ to LAB
     * @param labColor, The output LAB color
     * @param xyzColor, The input XYZ color
     */
    public static void xyzToLAB(float labColor[],  float xyzColor[])
    {

        double X = xyzColor[0];
        double Y = xyzColor[1];
        double Z = xyzColor[2];

        double iYn = 1.0f / 1.0f;
        double iXn = 1.0f / 0.950455f;
        double iZn = 1.0f / 1.088753f;

        // scale XYZ for normalized white
        X *= iXn;
        Y *= iYn;
        Z *= iZn;

        // Convert XYZ to LAB

        double U, V, W;

        if (X > 0.008856)
        {
            U = java.lang.Math.pow(X, 1.0/3.0);
        }
        else
        {
            U = (X * 7.787) + (16/116);
        }

        if (Y > 0.008856)
        {
            V = java.lang.Math.pow(Y, 1.0f/3.0f);
            labColor[0] = (float)((V  * 116.0) - 16.0);
        }
        else
        {
            V = (Y * 7.787f) + (16/116);
            labColor[0] = (float)(903.0 * Y);
        }
        if (Z > 0.008856)
        {
            W = java.lang.Math.pow(Z, 1.0/3.0);
        }
        else
        {
            W = (Z * 7.787) + (16/116);
        }

        labColor[1] = (float)(500.0 * (U - V));
        labColor[2] = (float)(200.0 * (V - W));
    }

    /**
     * Converts from LAB to sRGB
     * @param rgbColor, The output sRGB color
     * @param labColor, The input LAB color
     */
    public static void labToRGB(float rgbColor[],  float labColor[])
    {
        labToXYZ(rgbColor, labColor);
        xyzTolinRGB(rgbColor, rgbColor);
        linRGBToRGB(rgbColor, rgbColor);
    }

    /**
     * Converts from sRGB to LAB
     * @param labColor, The output LAB color
     * @param rgbColor, The input sRGB color
     */
    public static void rgbToLAB(float labColor[],  float rgbColor[])
    {
        rgbTolinRGB(labColor, rgbColor);
        linRGBToXYZ(labColor, labColor);
        xyzToLAB(labColor, labColor);
    }

    /**
     * Applies an inverse Gamma correction
     * @param n, The input value for correction.
     * @param gamma, The gamma to apply.
     * @returns the inverse gamma of n;
     */
    public static float gamma_inv(float n, float gamma)
    {
        if (n < 0.00001f)
        {
            n = 0.00001f;
        }
        n = (float)java.lang.Math.pow(n, 1.0/gamma);

        return(n);
    }

    /**
     * Applies a Gamma correction
     * @param n, The input value for correction.
     * @param gamma, The gamma to apply.
     * @returns the gamma of n;
     */
    public static  float gamma(float n, float gamma)
    {
        if (n < 0.00001f)
        {
            n = 0.00001f;
        }
        n = (float)java.lang.Math.pow(n, gamma);
        return(n);
    }

    /**
     * returns the luminance of an rgbColor
     * @param rgbColor, The input value for correction.
     * @returns the luminance of rgbColor;
     */
    public static float luminance(float rgbColor[])
    {
        float result = rgbColor[0] * 0.299f + rgbColor[1] * 0.587f + rgbColor[2] * 0.114f;
        return(result);
    }

    /**
     * returns the luminance of an rgbColor
     * @param luminance, The input luminance for adjustment.
     * @param whiteLuminance, The white point luminance
     * @param d, The display white.
     * @returns the adjusted luminance.
     */
    public static float applyWhiteLuminance(float luminance, float whiteLuminance, float d)
    {
        float result = 0;
        if (whiteLuminance > 0.0001f)
        {
            result = d/whiteLuminance;
        }
        result = luminance * result;
        result = java.lang.Math.min(d, result);
        result = java.lang.Math.max(0, result);
        return(result);
    }

    /**
     * returns the luminance of an rgbColor
     * @param adjColor, The input rgb color for adjustment.
     * @param rgbColor, The input rgb color for adjustment.
     * @param rgbWhitePoint, The white point color
     * @param d, The display white.
     * @returns the adjusted luminance.
     */
    public static void applyWhitePoint(float adjColor[], float rgbColor[], float rgbWhitePoint[], float d)
    {
        adjColor[0] = applyWhiteLuminance(rgbColor[0], rgbWhitePoint[0], d);
        adjColor[1] = applyWhiteLuminance(rgbColor[1], rgbWhitePoint[1], d);
        adjColor[2] = applyWhiteLuminance(rgbColor[2], rgbWhitePoint[2], d);
    }

    /**
     * returns the distance squared between the two colors.
     * @param color0, The first color to compare
     * @param color1, The second color to compare
     * @returns The distance squared between the two colors.
     */
    public static float colorDiff(float color0[], float color1[])
    {
        float diff = color0[0] - color1[0];
        float dist = diff*diff;
        diff = color0[1] - color1[1];
        dist += diff*diff;
        diff = color0[2] - color1[2];
        dist += diff*diff;
        return(dist);
    };

    /**
     * Converts from sRGB to LAB
     * @param labColor, The output LAB color
     * @param rgbColor, The input rgb color for adjustment.
     * @param rgbWhitePoint, The white point color
     * @param d, The display white.
     */
    public static void rgbToLABWhitepoint(float labColor[],  float rgbColor[], float rgbWhitePoint[], float d)
    {
        applyWhitePoint(labColor, rgbColor, rgbWhitePoint, d);
        rgbTolinRGB(labColor, labColor);
        linRGBToXYZ(labColor, labColor);
        xyzToLAB(labColor, labColor);
    }

}
