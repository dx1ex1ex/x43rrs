package tk.namstudio.x43rrs;

import android.view.MotionEvent;

import tk.namstudio.x43rrs.Core.D;
import tk.namstudio.x43rrs.Core.Log.L;
import tk.namstudio.x43rrs.Core.VectorAngle.A;
import tk.namstudio.x43rrs.Core.VectorAngle.V;
import tk.namstudio.x43rrs.Oggetti.OggMgr;
import tk.namstudio.x43rrs.Oggetti.Oggetto;


public class InputMgr
{
    public Oggetto pad;
    Oggetto b1, b2;
    V padDefaultPos;
    final float dist = .25f;
    boolean initialized = false;

    public InputMgr()
    {
        float scale = 0.05f;
        b1 = new Oggetto(new V(), new A(), new V(scale), OggMgr.TEX_ICON);
        D.drawabili.add(0,b1);

        b2 = new Oggetto(new V(),  new A(), new V(scale), OggMgr.TEX_ICON);
        D.drawabili.add(0,b2);

        padDefaultPos = new V();
        pad = new Oggetto(new V(), new A(), new V(scale), OggMgr.TEX_ICON);
        D.drawabili.add(0, pad);




    }

    public void Update()
    {
        if(!initialized)
        {
            padDefaultPos.Set(D.FromScreenToOGL(new V(1/5f*D.sWidth, .65f*D.sHeight)));
            b1.position.Set(D.FromScreenToOGL(new V(4/5f*D.sWidth, .25f*D.sHeight)));
            b2.position.Set(D.FromScreenToOGL(new V(4/5f*D.sWidth, .75f*D.sHeight)));

            pad.position.Set(padDefaultPos);
            initialized = true;
        }
    }

    public void OnTouch(MotionEvent e)
    {
        V v = new V(e.getRawX(),e.getRawY());
        v = D.FromScreenToOGL(v);

        float dd = v.Distance(padDefaultPos);


        if(v.x < 0)
        {
            L.i("si muoveee");
        }
        else
        {
            if(v.y < 0)
            {
                L.i("azione 1");

                pad.position.Set(padDefaultPos);
                return;
            }
            else
            {
                L.i("azione 2");

                pad.position.Set(padDefaultPos);
                return;
            }
        }
        if(e.getAction() == MotionEvent.ACTION_UP)
        {
            pad.position.Set(padDefaultPos);
            return;
        }


        pad.position.Set(v);
        if(dd > dist)
        {
            pad.position.thisMeno(padDefaultPos);
            pad.position.Normalize();
            pad.position.Mul(dist);
            pad.position.Sum(padDefaultPos);
        }

        dd = dd > dist? 1 : dd/dist;
        return;
    }
}
