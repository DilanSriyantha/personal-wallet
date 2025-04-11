import { Box, CircularProgress } from '@mui/material';
import nProgress from 'nprogress';
import { useEffect } from 'react';

function SuspenseLoader() {

    useEffect(() => {
        nProgress.start();

        return () => {
            nProgress.done();
        };
    }, []);

    return (
        <Box
            sx={{
                position: "fixed",
                left: 0,
                top: 0,
                width: "100%",
                height: "100%",
                display: "flex",
                justifyContent: "center",
                alignItems: "center"
            }}
        >
            <CircularProgress
                size={64}
                disableShrink
                thickness={3}
            />
        </Box>
    );
}

export default SuspenseLoader